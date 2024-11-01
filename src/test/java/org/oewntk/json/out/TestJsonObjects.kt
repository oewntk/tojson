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
import org.oewntk.model.Tracing

class TestJsonObjects {

    private val lemma = "jest"

    private val lemma2 = "jest2"

    private val sk1: SenseKey = "jest%1:00:01::"

    private val sk2: SenseKey = "jest%1:00:02::"

    private val synset = Synset(
        "77777777-n",
        'n',
        "domain",
        arrayOf(lemma, lemma2),
        arrayOf("definition", "definition2"),
    )

    private val lex = Lex(lemma, "n-1")

    private val lex2 = Lex(lemma, "n-2")

    private val sense = Sense(sk1, lex, 'n', 0, synset.synsetId)

    @Suppress("unused")
    private val sense2 = Sense(sk2, lex, 'n', 0, synset.synsetId)

    init {
        lex.apply { senseKeys = mutableListOf() }
        lex2.apply { senseKeys = mutableListOf(sk1, sk2) }
    }

    @Test
    fun testLex() {

        val jsonString = json.encodeToString(lex)
        println(jsonString)
    }

    @Test
    fun testLex2() {

        val jsonString = json.encodeToString(lex2)
        println(jsonString)
    }

    @Test
    fun testSense() {

        val jsonString = json.encodeToString(sense)
        println(jsonString)
    }

    @Test
    fun testSynset() {

        val jsonString = json.encodeToString(synset)
        println(jsonString)
    }

    companion object {

        @Suppress("unused")
        private val ps = if (!System.getProperties().containsKey("SILENT")) Tracing.psInfo else Tracing.psNull

        @OptIn(ExperimentalSerializationApi::class)
        val json = Json {
            prettyPrint = true
            prettyPrintIndent = "  " // Optional: Customize indentation (default is 4 spaces)
        }

        @Suppress("EmptyMethod")
        @JvmStatic
        @BeforeClass
        fun init() {
        }
    }
}
