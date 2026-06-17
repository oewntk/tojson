package org.oewntk.json.out

import org.oewntk.model.Lex
import org.oewntk.model.Pronunciation
import org.oewntk.model.Sense
import org.oewntk.model.Synset
import org.oewntk.model.SynsetType

object LibTestData {
    val lex = Lex(
        lemma = "jest",
        key2 = "v",
        senseKeys = listOf("jest%2:32:00::", "jest%2:29:00::")
    )
        .apply {
            pronunciations = setOf(Pronunciation("dʒəʊk", "GB"), Pronunciation("dʒoʊk", "US"))
        }

    val synset = Synset(
        synsetId = "00855315-v",
        type = SynsetType.V,
        domain = "communication",
        members = setOf("joke", "jest"),
        definitions = listOf("tell a joke", "speak humorously"),
        examples = listOf("He often jokes" to null),
    ).apply {
    }

    val sense = Sense("jest%2:32:00::", lex.key, "00855315-v")
}