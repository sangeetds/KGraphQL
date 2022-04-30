package org.sangeet.kgraphql.schema

interface Subscription {
    fun request(n: Long)
    fun cancel()
}