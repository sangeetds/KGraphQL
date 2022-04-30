package org.sangeet.kgraphql.demo

import org.sangeet.kgraphql.integration.QueryTest
import org.sangeet.kgraphql.server.NettyServer

/**
 * Demo application showing of tested schema, by default runs on localhost:8080
 */
fun main() {
    val schema = QueryTest().testedSchema
    NettyServer.run(schema, 8080)
}
