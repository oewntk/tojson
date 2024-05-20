/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

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

    private val sense2 = Sense(sk2, lex, 'n', 0, synset.synsetId)

    init {
        lex.apply { senseKeys = mutableListOf() }
        lex2.apply { senseKeys = mutableListOf(sk1, sk2) }
    }

    @Test
    fun testLex() {

        val jsonString = Serializer.prettyPrintLex(lex)
        ps.println(jsonString)
    }

    @Test
    fun testLex2() {

        val jsonString = Serializer.prettyPrintLex(lex2)
        ps.println(jsonString)
    }

    @Test
    fun testSense() {

        val jsonString = Serializer.prettyPrintSense(sense)
        ps.println(jsonString)
    }

    @Test
    fun testSense2() {

        val jsonString = Serializer.prettyPrintSense(sense2)
        ps.println(jsonString)
    }

    @Test
    fun testSynset() {

        val jsonString = Serializer.prettyPrintSynset(synset)
        ps.println(jsonString)
    }

    companion object {

        private val ps = if (!System.getProperties().containsKey("SILENT")) Tracing.psInfo else Tracing.psNull

        @Suppress("EmptyMethod")
        @JvmStatic
        @BeforeClass
        fun init() {
        }
    }
}
