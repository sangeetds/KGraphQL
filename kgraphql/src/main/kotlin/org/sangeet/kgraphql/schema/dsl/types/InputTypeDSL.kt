package org.sangeet.kgraphql.schema.dsl.types

import org.sangeet.kgraphql.defaultKQLTypeName
import org.sangeet.kgraphql.schema.dsl.ItemDSL
import kotlin.reflect.KClass


class InputTypeDSL<T : Any>(val kClass: KClass<T>) : ItemDSL() {

    var name = kClass.defaultKQLTypeName()
}
