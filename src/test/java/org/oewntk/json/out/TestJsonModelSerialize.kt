/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.BeforeClass
import org.junit.Test
import org.oewntk.model.Filename
import org.oewntk.model.LibTestGen.genModelSerializables
import org.oewntk.model.SData
import org.oewntk.ser.`in`.LibTestsSerCommon.checkOrig
import org.oewntk.ser.`in`.LibTestsSerCommon.model
import org.oewntk.ser.`in`.LibTestsSerCommon.ps

class TestYamlModelSerialize {

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        prettyPrint = true
        prettyPrintIndent = "  " // Optional: Customize indentation (default is 4 spaces)
    }

    @Test
    fun testModelSerialization() {
        val serialized: Sequence<Pair<SData, Filename>> = genModelSerializables(model)
        serialized.forEach { (sdata: SData, _: Filename) ->
            val yamlString = json.encodeToString(sdata)
            ps.println(yamlString)
        }
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
