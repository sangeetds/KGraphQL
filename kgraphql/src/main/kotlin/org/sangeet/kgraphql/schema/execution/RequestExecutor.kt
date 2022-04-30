package org.sangeet.kgraphql.schema.execution

import org.sangeet.kgraphql.Context
import org.sangeet.kgraphql.request.VariablesJson


interface RequestExecutor {
    suspend fun suspendExecute(plan : ExecutionPlan, variables: VariablesJson, context: Context): String
}
