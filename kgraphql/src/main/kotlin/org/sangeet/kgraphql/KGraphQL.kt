package org.sangeet.kgraphql

import org.sangeet.kgraphql.schema.Schema
import org.sangeet.kgraphql.schema.dsl.SchemaBuilder


class KGraphQL {
    companion object {
        fun schema(init: SchemaBuilder.() -> Unit): Schema {
            return SchemaBuilder()
                .apply(init)
                .build()
        }
    }
}