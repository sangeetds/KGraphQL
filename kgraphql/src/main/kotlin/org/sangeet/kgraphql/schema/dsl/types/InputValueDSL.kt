package org.sangeet.kgraphql.schema.dsl.types

import org.sangeet.kgraphql.schema.dsl.DepreciableItemDSL
import org.sangeet.kgraphql.schema.model.InputValueDef
import kotlin.reflect.KClass
import kotlin.reflect.KType


class InputValueDSL<T : Any>(val kClass: KClass<T>, val kType: KType? = null) : DepreciableItemDSL() {

    lateinit var name : String

    var defaultValue : T? = null

    fun toKQLInputValue() : InputValueDef<T> = InputValueDef(
            kClass = kClass,
            name = name,
            defaultValue = defaultValue,
            isDeprecated = isDeprecated,
            description = description,
            deprecationReason = deprecationReason,
            kType = kType
    )
}
