package org.sangeet.kgraphql

import org.sangeet.kgraphql.schema.model.ast.ASTNode

class ValidationException(message: String, nodes: List<ASTNode>? = null): GraphQLError(message, nodes = nodes)
