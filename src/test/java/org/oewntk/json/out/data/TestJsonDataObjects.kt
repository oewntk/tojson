package org.oewntk.json.out.data

import org.junit.BeforeClass
import org.junit.Test
import org.oewntk.json.out.JsonCodec
import org.oewntk.json.out.Method
import org.oewntk.model.Lex
import org.oewntk.model.LibModelSubset.lexSubset
import org.oewntk.model.LibModelSubset.synsetSubset
import org.oewntk.model.ModelInfo
import org.oewntk.model.Synset
import org.oewntk.model.SynsetType
import org.oewntk.model.toData
import org.oewntk.ser.`in`.LibTestsSerCommon
import java.io.File
import kotlin.test.assertEquals

class TestJsonDataObjects {

    val json = JsonCodec(method = Method.ANY_SERIALIZER, prettyPrint = true)

    @Test
    fun testDummyEmptyLex() {
        val lex = Lex("jest", "n").apply { senseKeys = mutableListOf() }
        val serializable: Map<String, Any> = lex.toData()
        val jsonString = json.encodeToString(serializable)
        LibTestsSerCommon.ps.println(jsonString)
    }

    @Test
    fun testDummyLex() {
        val lex = Lex("jest", "n", listOf("jest%1:10:00::", "jest%1:04:00::"))
        val serializable: Map<String, Any> = lex.toData()
        val jsonString = json.encodeToString(serializable)
        LibTestsSerCommon.ps.println(jsonString)
    }

    @Test
    fun testDummySynset() {
        val synset = Synset(
            "77777777-n",
            SynsetType.N,
            "domain",
            arrayOf("member1", "member2"),
            arrayOf("definition", "definition2"),
        )
        val serializable: Map<String, Any> = synset.toData()
        val jsonString = json.encodeToString(serializable)
        LibTestsSerCommon.ps.println(jsonString)
    }

    @Test
    fun testSense() {
        val sense = LibTestsSerCommon.model.senseResolver("jest%1:10:00::")
        val serializable: Map<String, Any> = sense.toData()
        val jsonString = json.encodeToString(serializable)
        LibTestsSerCommon.ps.println(jsonString)
    }

    @Test
    fun testSenses() {
        val someSenses = arrayOf("force%1:07:00::", "force%1:07:01::", "force%1:19:00::")
            .map(LibTestsSerCommon.model.senseResolver)
            .asSequence()
        val serializables: Sequence<Map<String, Any>> = someSenses.map { it.toData() }
        val jsonStrings: Sequence<String> = serializables.map { json.encodeToString(it) }
        LibTestsSerCommon.ps.println(jsonStrings.joinToString("\n\n"))
    }

    @Test
    fun testSynset() {
        val synset: Synset = LibTestsSerCommon.model.synsetResolver("05042508-n")
        val serializable: Map<String, Any> = synset.toData()
        val jsonString = json.encodeToString(serializable)
        LibTestsSerCommon.ps.println(jsonString)
    }

    @Test
    fun testSynsets() {
        val someSynsets = arrayOf("05042508-n", "05201846-n", "11479041-n")
            .map(LibTestsSerCommon.model.synsetResolver)
            .asSequence()
        val serializables: Sequence<Map<String, Any>> = someSynsets.map { it.toData() }
        val jsonStrings: Sequence<String> = serializables.map { json.encodeToString(it) }
        LibTestsSerCommon.ps.println(jsonStrings.joinToString("\n\n"))
    }

    @Test
    fun testRandomSynsets() {
        val someSynsets: Sequence<Synset> = LibTestsSerCommon.model.synsetSubset()
        val serializables: Sequence<Map<String, Any>> = someSynsets.map { it.toData() }
        val jsonStrings: Sequence<String> = serializables.map { json.encodeToString(it) }
        LibTestsSerCommon.ps.println(jsonStrings.joinToString("\n\n"))
    }

    @Test
    fun testLex() {
        val lex: Lex = LibTestsSerCommon.model.lexResolver1("jest", "n")
        val serializable: Map<String, Any> = lex.toData()
        val jsonString = json.encodeToString(serializable)
        LibTestsSerCommon.ps.println(jsonString)
    }

    @Test
    fun testLexes() {
        val someLexes = arrayOf("force", "lead", "row", "bow", "galore")
            .flatMap(LibTestsSerCommon.model.lexResolver)
            .asSequence()
        val serializables: Sequence<Map<String, Any>> = someLexes.map { it.toData() }
        val jsonStrings: Sequence<String> = serializables.map { json.encodeToString(it) }
        LibTestsSerCommon.ps.println(jsonStrings.joinToString("\n\n"))
    }

    @Test
    fun testRandomLexes() {
        val someLexes: Sequence<Lex> = LibTestsSerCommon.model.lexSubset()
        val serializables: Sequence<Map<String, Any>> = someLexes.map { it.toData() }
        val jsonStrings: Sequence<String> = serializables.map { json.encodeToString(it) }
        LibTestsSerCommon.ps.println(jsonStrings.joinToString("\n\n"))
    }

   // @Test
   // fun testSomeLexesByLemmaThenByKey2() {
   //     val someLexes: Sequence<Lex> = model.lexSubset(howMany = 5)
   //     val map: HyperMap1 = someLexes.lexByLemmaThenByKey2()
   //     val serializedMap: Map<Lemma, Any> = map.toData()
   //     val jsonString = json.encodeToString(serializedMap)
   //     ps.println(jsonString)
   // }

   // @Test
   // fun testSomeSynsetsBySynsetId() {
   //     val someSynsets: Sequence<Synset> = model.synsetSubset(howMany = 5)
   //     val map: Map<SynsetId, Synset> = someSynsets.synsetsById()
   //     val serializedMap: Map<SynsetId, Any> = map.toData()
   //     val jsonString = json.encodeToString(serializedMap)
   //     ps.println(jsonString)
   // }

    @Test
    fun testOrig() {
        val orig: String = System.getProperty("INFO")!!
        val origInfo = File(orig).readText()
        val info = LibTestsSerCommon.model.info()
        val counts = ModelInfo.counts(LibTestsSerCommon.model)
        val modelInfo = "$info\n$counts"
        LibTestsSerCommon.ps.println(modelInfo)
        assertEquals(origInfo, modelInfo)
    }

    companion object {

        @JvmStatic
        @BeforeClass
        fun init() {
            LibTestsSerCommon.model // eager
        }
    }
}