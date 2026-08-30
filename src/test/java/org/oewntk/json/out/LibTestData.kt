package org.oewntk.json.out

import org.oewntk.model.*

object LibTestData {
    val lex = Lex(
        lemma = "jest",
        key2 = "v",
        senseKeys = listOf(SenseKey("jest%2:32:00::"), SenseKey("jest%2:29:00::"))
    )
        .apply {
            pronunciations = setOf(Pronunciation(PronunciationValue("dʒəʊk"), "GB"), Pronunciation(PronunciationValue("dʒoʊk"), "US"))
        }

    val synset = Synset(
        synsetId = SynsetId("00855315-v"),
        type = SynsetType.V,
        domain = "communication",
        members = setOf("joke", "jest"),
        definitions = listOf("tell a joke", "speak humorously"),
        examples = listOf(Example("He often jokes", null)),
    ).apply {
    }

    val sense = Sense(SenseKey("jest%2:32:00::"), lex.key, SynsetId("00855315-v"))
}