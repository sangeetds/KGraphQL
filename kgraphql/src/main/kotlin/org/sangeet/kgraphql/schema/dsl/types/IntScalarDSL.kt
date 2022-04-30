package org.sangeet.kgraphql.schema.dsl.types

import org.sangeet.kgraphql.schema.SchemaException
import org.sangeet.kgraphql.schema.model.ast.ValueNode
import org.sangeet.kgraphql.schema.scalar.IntScalarCoercion
import org.sangeet.kgraphql.schema.scalar.ScalarCoercion
import kotlin.reflect.KClass


class IntScalarDSL<T : Any>(kClass: KClass<T>) : ScalarDSL<T, Int>(kClass) {

    override fun createCoercionFromFunctions(): ScalarCoercion<T, Int> {
        return object : IntScalarCoercion<T> {

            val serializeImpl = serialize ?: throw SchemaException(PLEASE_SPECIFY_COERCION)

            val deserializeImpl = deserialize ?: throw SchemaException(PLEASE_SPECIFY_COERCION)

            override fun serialize(instance: T): Int = serializeImpl(instance)

            override fun deserialize(raw: Int, valueNode: ValueNode?): T = deserializeImpl(raw)
        }
    }

}
