package org.sangeet.kgraphql.schema.model.ast

data class NameNode(
    val value: String,
    override val loc: Location?
): ASTNode()
