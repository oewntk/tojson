/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
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

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        prettyPrint = true
        prettyPrintIndent = "  " // Optional: Customize indentation (default is 4 spaces)
    }

    @Test
    fun testModelSerialization() {
        val (someLexes, someSynsets, someSenses) = model.subset()
        val dataLexes = someLexes.toLexesData()
        val dataSynsets = someSynsets.toSynsetsData()
        val dataSenses = someSenses.toSensesData()
        val jsonLexesString = json.encodeToString(KSData.serializer(), KSData(dataLexes))
        val jsonSynsetsString = json.encodeToString(KSData.serializer(), KSData(dataSynsets))
        val jsonSensesString = json.encodeToString(KSData.serializer(), KSData(dataSenses))
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
