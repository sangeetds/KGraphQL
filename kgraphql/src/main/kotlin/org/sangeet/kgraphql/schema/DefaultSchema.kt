package org.sangeet.kgraphql.schema

import org.sangeet.kgraphql.Context
import org.sangeet.kgraphql.GraphQLError
import org.sangeet.kgraphql.configuration.SchemaConfiguration
import org.sangeet.kgraphql.request.CachingDocumentParser
import org.sangeet.kgraphql.request.VariablesJson
import org.sangeet.kgraphql.schema.introspection.__Schema
import org.sangeet.kgraphql.request.Parser
import org.sangeet.kgraphql.schema.execution.*
import org.sangeet.kgraphql.schema.execution.Executor.*
import org.sangeet.kgraphql.schema.model.ast.NameNode
import org.sangeet.kgraphql.schema.structure.LookupSchema
import org.sangeet.kgraphql.schema.structure.RequestInterpreter
import org.sangeet.kgraphql.schema.structure.SchemaModel
import org.sangeet.kgraphql.schema.structure.Type
import kotlinx.coroutines.coroutineScope
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.jvm.jvmErasure

class DefaultSchema (
        override val configuration: SchemaConfiguration,
        internal val model : SchemaModel
) : Schema , __Schema by model, LookupSchema {

    companion object {
        val OPERATION_NAME_PARAM = NameNode("operationName", null)
    }

    private val defaultRequestExecutor: RequestExecutor = getExecutor(configuration.executor)

    private fun getExecutor(executor: Executor) = when (executor) {
        Parallel -> ParallelRequestExecutor(this)
        DataLoaderPrepared -> DataLoaderPreparedRequestExecutor(this)
    }

     private val requestInterpreter : RequestInterpreter = RequestInterpreter(model)

    private val cacheParser: CachingDocumentParser by lazy { CachingDocumentParser(configuration.documentParserCacheMaximumSize) }

    override suspend fun execute(request: String, variables: String?, context: Context, options: ExecutionOptions): String = coroutineScope {
        val parsedVariables = variables
            ?.let { VariablesJson.Defined(configuration.objectMapper, variables) }
            ?: VariablesJson.Empty()

        val document = Parser(request).parseDocument()

        val executor = options.executor?.let(this@DefaultSchema::getExecutor) ?: defaultRequestExecutor

        executor.suspendExecute(
            plan = requestInterpreter.createExecutionPlan(document, parsedVariables, options),
            variables = parsedVariables,
            context = context
        )
    }

    override fun typeByKClass(kClass: KClass<*>): Type? = model.queryTypes[kClass]

    override fun typeByKType(kType: KType): Type? = typeByKClass(kType.jvmErasure)

    override fun inputTypeByKClass(kClass: KClass<*>): Type? = model.inputTypes[kClass]

    override fun inputTypeByKType(kType: KType): Type? = typeByKClass(kType.jvmErasure)

    override fun typeByName(name: String): Type? = model.queryTypesByName[name]

    override fun inputTypeByName(name: String): Type? = model.inputTypesByName[name]
}
