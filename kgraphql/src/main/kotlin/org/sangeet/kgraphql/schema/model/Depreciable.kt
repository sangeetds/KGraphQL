package org.sangeet.kgraphql.schema.model


interface Depreciable {

    val isDeprecated: Boolean

    val deprecationReason : String?
}