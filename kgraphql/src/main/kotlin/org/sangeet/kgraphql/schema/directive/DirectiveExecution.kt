package org.sangeet.kgraphql.schema.directive

import org.sangeet.kgraphql.schema.model.FunctionWrapper


class DirectiveExecution(val function: FunctionWrapper<DirectiveResult>) : FunctionWrapper<DirectiveResult> by function