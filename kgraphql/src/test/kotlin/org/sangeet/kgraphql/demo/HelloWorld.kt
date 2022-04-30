package org.sangeet.kgraphql.demo

import org.sangeet.kgraphql.KGraphQL

fun main() {
    val schema = KGraphQL.schema {
        query("hello") {
            resolver { name : String -> "Hello, $name" }
        }
    }

    //prints '{"data":{"hello":"Hello, Ted Mosby"}}'
    println(schema.executeBlocking("{hello(name : \"Ted Mosby\")}"))
}
