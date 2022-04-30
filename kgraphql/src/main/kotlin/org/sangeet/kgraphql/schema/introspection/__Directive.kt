package org.sangeet.kgraphql.schema.introspection

import org.sangeet.kgraphql.schema.directive.DirectiveLocation


interface __Directive : __Described {

    val locations : List<DirectiveLocation>

    val args: List<__InputValue>
}