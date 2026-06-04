package org.oewntk.json.out.oewn

import org.junit.BeforeClass
import org.junit.Test
import org.oewntk.json.out.JsonCodec
import org.oewntk.model.Filename
import org.oewntk.model.LibTestGen
import org.oewntk.ser.`in`.LibTestsSerCommon

class TestJsonModelOEWNSerialize {

    val json = JsonCodec(prettyPrintFlag = true)

    @Test
    fun testModelSerialization() {
        val serialized: Sequence<Pair<Map<String, Any>, Filename>> = LibTestGen.genModelSerializables(LibTestsSerCommon.model)
        serialized.forEach { (data: Map<String, Any>, _: Filename) ->
            val jsonString = json.encodeToString(data)
            LibTestsSerCommon.ps.println(jsonString)
        }
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