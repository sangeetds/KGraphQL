package org.sangeet.kgraphql.schema.dsl

import org.sangeet.kgraphql.Context
import org.sangeet.kgraphql.schema.model.FunctionWrapper
import org.sangeet.kgraphql.schema.model.InputValueDef
import org.sangeet.kgraphql.schema.model.PropertyDef
import java.lang.IllegalArgumentException
import kotlin.reflect.KType


class PropertyDSL<T : Any, R>(val name : String, block : PropertyDSL<T, R>.() -> Unit) : LimitedAccessItemDSL<T>(), ResolverDSL.Target {

    internal lateinit var functionWrapper : FunctionWrapper<R>

    private val inputValues = mutableListOf<InputValueDef<*>>()

    init {
        block()
    }

    private fun resolver(function: FunctionWrapper<R>): ResolverDSL {
        functionWrapper = function
        return ResolverDSL(this)
    }

    fun resolver(function: suspend (T) -> R)
            = resolver(FunctionWrapper.on(function, true))

    fun <E> resolver(function: suspend (T, E) -> R)
            = resolver(FunctionWrapper.on(function, true))

    fun <E, W> resolver(function: suspend (T, E, W) -> R)
            = resolver(FunctionWrapper.on(function, true))

    fun <E, W, Q> resolver(function: suspend (T, E, W, Q) -> R)
            = resolver(FunctionWrapper.on(function, true))

    fun <E, W, Q, A> resolver(function: suspend (T, E, W, Q, A) -> R)
            = resolver(FunctionWrapper.on(function, true))

    fun <E, W, Q, A, S> resolver(function: suspend (T, E, W, Q, A, S) -> R)
            = resolver(FunctionWrapper.on(function, true))

    fun accessRule(rule: (T, Context) -> Exception?){

        val accessRuleAdapter: (T?, Context) -> Exception? = { parent, ctx ->
            if (parent != null) rule(parent, ctx) else IllegalArgumentException("Unexpected null parent of kotlin property")
        }

        this.accessRuleBlock = accessRuleAdapter
    }

    fun toKQLProperty() = PropertyDef.Function<T, R>(
            name = name,
            resolver = functionWrapper,
            description = description,
            isDeprecated = isDeprecated,
            deprecationReason = deprecationReason,
            inputValues = inputValues,
            accessRule = accessRuleBlock
    )

    override fun addInputValues(inputValues: Collection<InputValueDef<*>>) {
        this.inputValues.addAll(inputValues)
    }

    override fun setReturnType(type: KType) {
        // NOOP
    }
}
