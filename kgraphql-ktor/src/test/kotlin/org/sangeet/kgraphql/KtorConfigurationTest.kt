package org.sangeet.kgraphql

import org.sangeet.kgraphql.schema.execution.Executor
import io.ktor.server.application.install
import io.ktor.server.testing.withTestApplication
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test

class KtorConfigurationTest: KtorTest() {

    @Test
    fun `default configuration should`() {
        var checked = false
        withTestApplication({
            val config = install(GraphQL) {}
            checked = true
            config.schema.configuration.executor shouldBeEqualTo Executor.Parallel
        }) {}
        checked shouldBeEqualTo true
    }

    @Test
    fun `update configuration`() {
        var checked = false
        withTestApplication({
            val config = install(GraphQL) {
                executor = Executor.DataLoaderPrepared
            }
            checked = true
            config.schema.configuration.executor shouldBeEqualTo Executor.DataLoaderPrepared
        }) {}
        checked shouldBeEqualTo true
    }

}
