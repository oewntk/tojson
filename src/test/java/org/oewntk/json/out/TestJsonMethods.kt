package org.oewntk.json.out

import org.junit.BeforeClass
import org.junit.Test
import org.oewntk.model.*
import org.oewntk.ser.`in`.Tracing
import java.io.PrintStream

class TestJsonMethods {

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
        members = arrayOf("joke", "jest"),
        definitions = arrayOf("tell a joke", "speak humorously"),
        examples = arrayOf("He often jokes" to null),
    ).apply {
    }

    val sense = Sense("jest%2:32:00::", lex, SynsetType.V, 0, "00855315-v")

    @Test
    fun testDummyLex() {
        val json = JsonCodec(jsonMethod = JsonMethod.ANY_SERIALIZER, prettyPrint = true)
        val serializable: Map<String, Any> = lex.toData()
        val jsonString = json.encodeToString(serializable)
        ps.println(jsonString)
    }

    @Test
    fun testDummySynset() {
        val json = JsonCodec(jsonMethod = JsonMethod.ANY_SERIALIZER, prettyPrint = true)
        val serializable: Map<String, Any> = synset.toData()
        val jsonString = json.encodeToString(serializable)
        ps.println(jsonString)
    }

    @Test
    fun testDummySense() {
        val json = JsonCodec(jsonMethod = JsonMethod.ANY_SERIALIZER, prettyPrint = true)
        val serializable: Map<String, Any> = sense.toData()
        val jsonString = json.encodeToString(serializable)
        ps.println(jsonString)
    }

    @Test
    fun testJsonElementDummyLex() {
        val json = JsonCodec(jsonMethod = JsonMethod.JSON_ELEMENT, prettyPrint = true)
        val serializable: Map<String, Any> = lex.toData()
        val jsonString = json.encodeToString(serializable)
        ps.println(jsonString)
    }

    @Test
    fun testJsonElementDummySynset() {
        val json = JsonCodec(jsonMethod = JsonMethod.JSON_ELEMENT, prettyPrint = true)
        val serializable: Map<String, Any> = synset.toData()
        val jsonString = json.encodeToString(serializable)
        ps.println(jsonString)
    }

    @Test
    fun testJsonElementDummySense() {
        val json = JsonCodec(jsonMethod = JsonMethod.JSON_ELEMENT, prettyPrint = true)
        val serializable: Map<String, Any> = sense.toData()
        val jsonString = json.encodeToString(serializable)
        ps.println(jsonString)
    }

    @Test
    fun testValueWrapperDummyLex() {
        val json = JsonCodec(jsonMethod = JsonMethod.VALUE_WRAPPER, prettyPrint = true)
        val serializable: Map<String, Any> = lex.toData()
        val jsonString = json.encodeToString(serializable)
        ps.println(jsonString)
    }

    @Test
    fun testValueWrapperDummySynset() {
        val json = JsonCodec(jsonMethod = JsonMethod.VALUE_WRAPPER, prettyPrint = true)
        val serializable: Map<String, Any> = synset.toData()
        val jsonString = json.encodeToString(serializable)
        ps.println(jsonString)
    }

    @Test
    fun testValueWrapperDummySense() {
        val json = JsonCodec(jsonMethod = JsonMethod.VALUE_WRAPPER, prettyPrint = true)
        val serializable: Map<String, Any> = sense.toData()
        val jsonString = json.encodeToString(serializable)
        ps.println(jsonString)
    }

    companion object {
        val silent = if (System.getProperties().containsKey("VERBOSE")) false
        else if (System.getProperties().containsKey("SILENT")) true
        else true

        val ps: PrintStream = if (!silent) Tracing.psInfo else Tracing.psNull

        @JvmStatic
        @BeforeClass
        fun init() {
        }
    }
}