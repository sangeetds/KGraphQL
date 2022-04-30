package org.sangeet.kgraphql.schema

import org.sangeet.kgraphql.Context
import org.sangeet.kgraphql.configuration.SchemaConfiguration
import org.sangeet.kgraphql.schema.execution.ExecutionOptions
import org.sangeet.kgraphql.schema.introspection.__Schema
import kotlinx.coroutines.runBlocking
import org.intellij.lang.annotations.Language

interface Schema : __Schema {
    val configuration: SchemaConfiguration

    suspend fun execute(
        @Language("graphql") request: String,
        variables: String? = null,
        context: Context = Context(emptyMap()),
        options: ExecutionOptions = ExecutionOptions()
    ) : String

    fun executeBlocking(
        @Language("graphql") request: String,
        variables: String? = null,
        context: Context = Context(emptyMap()),
        options: ExecutionOptions = ExecutionOptions()
    ) = runBlocking { execute(request, variables, context, options) }
}
