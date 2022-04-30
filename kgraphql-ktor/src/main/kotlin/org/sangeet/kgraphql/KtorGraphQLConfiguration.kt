package org.sangeet.kgraphql

import org.sangeet.kgraphql.configuration.PluginConfiguration

class KtorGraphQLConfiguration(
    val playground: Boolean,
    val endpoint: String
): PluginConfiguration
