package org.oewntk.json.out.data

import org.junit.BeforeClass
import org.junit.Test
import org.oewntk.json.out.JsonCodec
import org.oewntk.json.out.JsonMethod
import org.oewntk.model.LibModelSubset.subset
import org.oewntk.model.toLexesData
import org.oewntk.model.toSensesData
import org.oewntk.model.toSynsetsData
import org.oewntk.ser.`in`.LibTestsSerCommon
import kotlin.collections.asSequence

class TestJsonModelDataSerialize {

    val json = JsonCodec(jsonMethod = JsonMethod.ANY_SERIALIZER, prettyPrint = true)

    @Test
    fun testModelSerialization() {
        val (someLexes, someSynsets, someSenses) = LibTestsSerCommon.model.subset()
        val dataLexes = someLexes.asSequence().toLexesData()
        val dataSynsets = someSynsets.asSequence().toSynsetsData()
        val dataSenses = someSenses.asSequence().toSensesData()
        val jsonLexesString = json.encodeToString(dataLexes)
        val jsonSynsetsString = json.encodeToString(dataSynsets)
        val jsonSensesString = json.encodeToString(dataSenses)
        LibTestsSerCommon.ps.println(jsonLexesString)
        LibTestsSerCommon.ps.println(jsonSynsetsString)
        LibTestsSerCommon.ps.println(jsonSensesString)
    }

    @Test
    fun testOrig() {
        LibTestsSerCommon.checkOrig()
    }

    companion object {

        @JvmStatic
        @BeforeClass
        fun init() {
            LibTestsSerCommon.model //eager
        }
    }
}