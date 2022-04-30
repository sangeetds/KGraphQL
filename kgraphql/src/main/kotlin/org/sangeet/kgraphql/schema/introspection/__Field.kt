package org.sangeet.kgraphql.schema.introspection

import org.sangeet.kgraphql.schema.model.Depreciable


interface __Field : Depreciable, __Described {

    val type: __Type

    val args: List<__InputValue>
}