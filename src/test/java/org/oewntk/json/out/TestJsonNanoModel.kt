/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

import org.junit.BeforeClass
import org.junit.Test
import org.oewntk.model.Tracing

class TestJsonNanoModel {

    @Test
    fun testLex1() {
        val jsonString = Serializer.prettyPrintLex(LibNanoModel.lex1)
        ps.println(jsonString)
    }

    @Test
    fun testLex2() {
        val jsonString = Serializer.prettyPrintLex(LibNanoModel.lex2)
        ps.println(jsonString)
    }

    @Test
    fun testSynset1() {
        val jsonString = Serializer.prettyPrintSynset(LibNanoModel.synset1)
        ps.println(jsonString)
    }

    @Test
    fun testSynset2() {
        val jsonString = Serializer.prettyPrintSynset(LibNanoModel.synset2)
        ps.println(jsonString)
    }

    @Test
    fun testSense11() {
        val jsonString = Serializer.prettyPrintSense(LibNanoModel.sense11)
        ps.println(jsonString)
    }

    @Test
    fun testSense12() {
        val jsonString = Serializer.prettyPrintSense(LibNanoModel.sense12)
        ps.println(jsonString)
    }

    @Test
    fun testSense21() {
        val jsonString = Serializer.prettyPrintSense(LibNanoModel.sense21)
        ps.println(jsonString)
    }

    @Test
    fun testSense22() {
        val jsonString = Serializer.prettyPrintSense(LibNanoModel.sense22)
        ps.println(jsonString)
    }

    @Test
    fun testPronunciation1() {
        val jsonString = Serializer.prettyPrintPronunciation(LibNanoModel.pronunciation1)
        ps.println(jsonString)
    }

    @Test
    fun testPronunciation21() {
        val jsonString = Serializer.prettyPrintPronunciation(LibNanoModel.pronunciation21)
        ps.println(jsonString)
    }

    @Test
    fun testPronunciation22() {
        val jsonString = Serializer.prettyPrintPronunciation(LibNanoModel.pronunciation22)
        ps.println(jsonString)
    }

    @Test
    fun testModel() {
        val jsonString = Serializer.prettyPrintCoreModel(LibNanoModel.model)
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
