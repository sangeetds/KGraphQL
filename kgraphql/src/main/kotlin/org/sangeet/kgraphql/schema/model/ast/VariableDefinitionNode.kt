package org.sangeet.kgraphql.schema.model.ast

import org.sangeet.kgraphql.schema.model.ast.ValueNode.*

data class VariableDefinitionNode(
    override val loc: Location?,
    val variable: VariableNode,
    val type: TypeNode,
    val defaultValue: ValueNode?,
    val directives: List<DirectiveNode>?
): ASTNode()
