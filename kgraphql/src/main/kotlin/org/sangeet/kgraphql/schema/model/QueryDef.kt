package org.sangeet.kgraphql.schema.model

import org.sangeet.kgraphql.Context
import kotlin.reflect.KType

class QueryDef<R> (
        name : String,
        resolver: FunctionWrapper<R>,
        override val description : String? = null,
        override val isDeprecated: Boolean = false,
        override val deprecationReason: String? = null,
        accessRule: ((Nothing?, Context) -> Exception?)? = null,
        inputValues : List<InputValueDef<*>> = emptyList(),
        explicitReturnType: KType? = null
) : BaseOperationDef<Nothing, R>(name, resolver, inputValues, accessRule, explicitReturnType), DescribedDef