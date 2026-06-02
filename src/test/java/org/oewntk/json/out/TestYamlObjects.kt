/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.BeforeClass
import org.junit.Test
import org.oewntk.model.*
import org.oewntk.model.Lex.Groups.lexByLemmaThenByKey2
import org.oewntk.model.LibModelSubset.lexSubset
import org.oewntk.model.LibModelSubset.synsetSubset
import org.oewntk.model.MapFactory.synsetsById
import org.oewntk.ser.`in`.LibTestsSerCommon.model
import org.oewntk.ser.`in`.LibTestsSerCommon.ps
import java.io.File
import kotlin.test.assertEquals

class TestYamlObjects {

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        prettyPrint = true
        prettyPrintIndent = "  " // Optional: Customize indentation (default is 4 spaces)
    }

    @Test
    fun testDummyEmptyLex() {
        val lex = Lex("jest", "n").apply { senseKeys = mutableListOf() }
        val serializable: Map<String, Any> = lex.toSerializable(model.senseResolver)
        val yamlString = json.encodeToString(serializable)
        ps.println(yamlString)
    }

    @Test
    fun testDummyLex() {
        val lex = Lex("jest", "n", listOf("jest%1:10:00::", "jest%1:04:00::"))
        val serializable: Map<String, Any> = lex.toSerializable(model.senseResolver)
        val yamlString = json.encodeToString(serializable)
        ps.println(yamlString)
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
        val serializable: Map<String, Any> = synset.toSerializable()
        val yamlString = json.encodeToString(serializable)
        ps.println(yamlString)
    }

    @Test
    fun testSense() {
        val sense = model.senseResolver("jest%1:10:00::")
        val serializable: Map<String, Any> = sense.toSerializable()
        val yamlString = json.encodeToString(serializable)
        ps.println(yamlString)
    }

    @Test
    fun testSenses() {
        val someSenses = arrayOf("force%1:07:00::", "force%1:07:01::", "force%1:19:00::")
            .map(model.senseResolver)
            .asSequence()
        val serializables: Sequence<Map<String, Any>> = someSenses.map { it.toSerializable() }
        val yamlStrings: Sequence<String> = serializables.map { json.encodeToString(it) }
        ps.println(yamlStrings.joinToString("\n\n"))
    }

    @Test
    fun testSynset() {
        val synset: Synset = model.synsetResolver("05042508-n")
        val serializable: Map<String, Any> = synset.toSerializable()
        val yamlString = json.encodeToString(serializable)
        ps.println(yamlString)
    }

    @Test
    fun testSynsets() {
        val someSynsets = arrayOf("05042508-n", "05201846-n", "11479041-n")
            .map(model.synsetResolver)
            .asSequence()
        val serializables: Sequence<Map<String, Any>> = someSynsets.map { it.toSerializable() }
        val yamlStrings: Sequence<String> = serializables.map { json.encodeToString(it) }
        ps.println(yamlStrings.joinToString("\n\n"))
    }

    @Test
    fun testRandomSynsets() {
        val someSynsets: Sequence<Synset> = model.synsetSubset()
        val serializables: Sequence<Map<String, Any>> = someSynsets.map { it.toSerializable() }
        val yamlStrings: Sequence<String> = serializables.map { json.encodeToString(it) }
        ps.println(yamlStrings.joinToString("\n\n"))
    }

    @Test
    fun testLex() {
        val lex: Lex = model.lexResolver1("jest", "n")
        val serializable: Map<String, Any> = lex.toSerializable(model.senseResolver)
        val yamlString = json.encodeToString(serializable)
        ps.println(yamlString)
    }

    @Test
    fun testLexes() {
        val someLexes = arrayOf("force", "lead", "row", "bow", "galore")
            .flatMap(model.lexResolver)
            .asSequence()
        val serializables: Sequence<Map<String, Any>> = someLexes.map { it.toSerializable(model.senseResolver) }
        val yamlStrings: Sequence<String> = serializables.map { json.encodeToString(it) }
        ps.println(yamlStrings.joinToString("\n\n"))
    }

    @Test
    fun testRandomLexes() {
        val someLexes: Sequence<Lex> = model.lexSubset()
        val serializables: Sequence<Map<String, Any>> = someLexes.map { it.toSerializable(model.senseResolver) }
        val yamlStrings: Sequence<String> = serializables.map { json.encodeToString(it) }
        ps.println(yamlStrings.joinToString("\n\n"))
    }

    @Test
    fun testSomeLexesByLemmaThenByKey2() {
        val someLexes: Sequence<Lex> = model.lexSubset(howMany = 5)
        val map: HyperMap1 = someLexes.lexByLemmaThenByKey2()
        val serializedMap: Map<Lemma, Any> = map.toSerializable(model.senseResolver)
        val yamlString = json.encodeToString(serializedMap)
        ps.println(yamlString)
    }

    @Test
    fun testSomeSynsetsBySynsetId() {
        val someSynsets: Sequence<Synset> = model.synsetSubset(howMany = 5)
        val map: Map<SynsetId,Synset> = someSynsets.synsetsById()
        val serializedMap: Map<SynsetId, Any> = map.toSerializable()
        val yamlString = json.encodeToString(serializedMap)
        ps.println(yamlString)
    }

    @Test
    fun testOrig() {
        val orig: String = System.getProperty("INFO")!!
        val origInfo = File(orig).readText()
        val info = model.info()
        val counts = ModelInfo.counts(model)
        val modelInfo = "$info\n$counts"
        ps.println(modelInfo)
        assertEquals(origInfo, modelInfo)
    }

    companion object {

        @JvmStatic
        @BeforeClass
        fun init() {
            model // eager
        }
    }
}
