package org.sangeet.kgraphql.schema.dsl.types

import org.sangeet.kgraphql.schema.SchemaException
import org.sangeet.kgraphql.schema.model.ast.ValueNode
import org.sangeet.kgraphql.schema.scalar.BooleanScalarCoercion
import org.sangeet.kgraphql.schema.scalar.ScalarCoercion
import kotlin.reflect.KClass


class BooleanScalarDSL<T : Any>(kClass: KClass<T>) : ScalarDSL<T, Boolean>(kClass) {

    override fun createCoercionFromFunctions(): ScalarCoercion<T, Boolean> {
        return object : BooleanScalarCoercion<T> {

            val serializeImpl = serialize ?: throw SchemaException(PLEASE_SPECIFY_COERCION)

            val deserializeImpl = deserialize ?: throw SchemaException(PLEASE_SPECIFY_COERCION)

            override fun serialize(instance: T): Boolean = serializeImpl(instance)

            override fun deserialize(raw: Boolean, valueNode: ValueNode?): T = deserializeImpl(raw)
        }
    }
}
