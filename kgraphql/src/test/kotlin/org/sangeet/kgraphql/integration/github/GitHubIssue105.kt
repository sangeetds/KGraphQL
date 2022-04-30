package org.sangeet.kgraphql.integration.github

import org.sangeet.kgraphql.KGraphQL
import org.sangeet.kgraphql.deserialize
import org.sangeet.kgraphql.extract
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.time.Instant

class GitHubIssue105 {

    sealed class ContactStatus {
        data class NotInvited(
            val dummy: String = "dummy" // because no empty types in gql
        ) : ContactStatus()

        data class Invited(
            val invitationId: String,
            val invitedAt: Instant
        ) : ContactStatus()

        data class Onboarded(
            val onboardedAt: Instant = Instant.now(),
            val userId: String
        ) : ContactStatus()
    }

    data class Carrier(
        val contactStatus: ContactStatus
    )

    @Test
    fun `Sealed classes unions should allow requesting __typename`() {
        val schema = KGraphQL.schema {
            longScalar<Instant> {
                serialize = { it.toEpochMilli() }
                deserialize = { Instant.ofEpochMilli(it) }
            }
            unionType<ContactStatus>()
            query("contactStatus") {
                resolver { -> ContactStatus.Onboarded(userId = "Leopard2A5") }
            }
        }
        val results = schema.executeBlocking("""{
            contactStatus {
                ... on NotInvited {
                    dummy
                }
                ... on Invited {
                    invitedAt
                    invitationId
                }
                ... on Onboarded {
                    onboardedAt
                    userId
                }
                __typename
            }
        }""".trimIndent()).also(::println).deserialize()

        results.extract<String>("data/contactStatus/userId") shouldBeEqualTo "Leopard2A5"
        results.extract<String>("data/contactStatus/__typename") shouldBeEqualTo "Onboarded"
    }

    @Test
    fun `Inner sealed classes unions should allow requesting __typename`() {
        val schema = KGraphQL.schema {
            longScalar<Instant> {
                serialize = { it.toEpochMilli() }
                deserialize = { Instant.ofEpochMilli(it) }
            }
            unionType<ContactStatus>()
            query("carrier") {
                resolver { -> Carrier(ContactStatus.Onboarded(userId = "Leopard2A5")) }
            }
        }

        val results = schema.executeBlocking("""{
            carrier {
                contactStatus {
                    ... on NotInvited {
                        dummy
                    }
                    ... on Invited {
                        invitedAt
                        invitationId
                    }
                    ... on Onboarded {
                        onboardedAt
                        userId
                    }
                    __typename
                }
            }
        }""".trimIndent()).also(::println).deserialize()

        results.extract<String>("data/carrier/contactStatus/userId") shouldBeEqualTo "Leopard2A5"
        results.extract<String>("data/carrier/contactStatus/__typename") shouldBeEqualTo "Onboarded"
    }
}
