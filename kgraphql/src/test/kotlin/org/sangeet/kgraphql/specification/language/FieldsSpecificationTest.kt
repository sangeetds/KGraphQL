package org.sangeet.kgraphql.specification.language

import org.sangeet.kgraphql.Actor
import org.sangeet.kgraphql.Specification
import org.sangeet.kgraphql.defaultSchema
import org.sangeet.kgraphql.deserialize
import org.sangeet.kgraphql.extract
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Test

@Specification("2.5 Fields")
class FieldsSpecificationTest {

    data class ActorWrapper(val id : String, val actualActor: Actor)

    val age = 432

    val schema = defaultSchema {
        query("actor") {
            resolver { -> ActorWrapper("BLinda", Actor("Boguś Linda", age)) }
        }
    }

    @Test
    fun `field may itself contain a selection set`() {
        val response = deserialize(schema.executeBlocking("{actor{id, actualActor{name, age}}}"))
        val map = response.extract<Map<String, Any>>("data/actor/actualActor")
        MatcherAssert.assertThat(map, CoreMatchers.equalTo(mapOf("name" to "Boguś Linda", "age" to age)))
    }
}

