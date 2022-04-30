package org.sangeet.kgraphql.schema.dsl

import org.sangeet.kgraphql.Context


abstract class LimitedAccessItemDSL<PARENT> : DepreciableItemDSL() {

    internal var accessRuleBlock: ((PARENT?, Context) -> Exception?)? = null

//    fun accessRule(rule: (PARENT?, Context) -> Exception?){
//        this.accessRuleBlock = rule
//    }
}
