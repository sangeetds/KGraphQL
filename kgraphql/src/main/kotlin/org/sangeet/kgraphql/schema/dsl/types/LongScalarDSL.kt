package org.sangeet.kgraphql.schema.dsl.types

import org.sangeet.kgraphql.schema.SchemaException
import org.sangeet.kgraphql.schema.model.ast.ValueNode
import org.sangeet.kgraphql.schema.scalar.LongScalarCoercion
import org.sangeet.kgraphql.schema.scalar.ScalarCoercion
import kotlin.reflect.KClass


class LongScalarDSL<T : Any>(kClass: KClass<T>) : ScalarDSL<T, Long>(kClass) {

    override fun createCoercionFromFunctions(): ScalarCoercion<T, Long> {
        return object : LongScalarCoercion<T> {

            val serializeImpl = serialize ?: throw SchemaException(PLEASE_SPECIFY_COERCION)

            val deserializeImpl = deserialize ?: throw SchemaException(PLEASE_SPECIFY_COERCION)

            override fun serialize(instance: T): Long = serializeImpl(instance)

            override fun deserialize(raw: Long, valueNode: ValueNode?): T = deserializeImpl(raw)
        }
    }

}
