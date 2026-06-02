/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

import org.junit.BeforeClass
import org.junit.Test
import org.oewntk.model.LibModelSubset.subset
import org.oewntk.model.toLexesData
import org.oewntk.model.toSensesData
import org.oewntk.model.toSynsetsData
import org.oewntk.ser.`in`.LibTestsSerCommon.checkOrig
import org.oewntk.ser.`in`.LibTestsSerCommon.model
import org.oewntk.ser.`in`.LibTestsSerCommon.ps

class TestJsonModelDataSerialize {

    val json = JsonCodec(prettyPrintFlag = true)

    @Test
    fun testModelSerialization() {
        val (someLexes, someSynsets, someSenses) = model.subset()
        val dataLexes = someLexes.toLexesData()
        val dataSynsets = someSynsets.toSynsetsData()
        val dataSenses = someSenses.toSensesData()
        val jsonLexesString = json.encodeToString(dataLexes)
        val jsonSynsetsString = json.encodeToString(dataSynsets)
        val jsonSensesString = json.encodeToString(dataSenses)
        ps.println(jsonLexesString)
        ps.println(jsonSynsetsString)
        ps.println(jsonSensesString)
    }

    @Test
    fun testOrig() {
        checkOrig()
    }

    companion object {

        @JvmStatic
        @BeforeClass
        fun init() {
            model //eager
        }
    }
}
