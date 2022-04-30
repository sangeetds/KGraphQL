package org.sangeet.kgraphql.request

import org.sangeet.kgraphql.GraphQLError
import org.sangeet.kgraphql.schema.model.ast.Source

internal fun syntaxError(
    source: Source,
    position: Int,
    description: String
) = GraphQLError(
    message = "Syntax Error: $description",
    nodes = null,
    source = source,
    positions = listOf(position)
)
