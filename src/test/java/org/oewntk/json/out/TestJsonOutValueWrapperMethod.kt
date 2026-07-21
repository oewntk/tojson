package org.oewntk.json.out

import org.junit.Test
import org.oewntk.json.out.LibTestData.lex
import org.oewntk.json.out.LibTestData.sense
import org.oewntk.json.out.LibTestData.synset
import org.oewntk.model.toData
import org.oewntk.ser.`in`.Tracing
import java.io.PrintStream

class TestJsonOutValueWrapperMethod {

    val json = JsonCodec(jsonMethod = JsonMethod.VALUE_WRAPPER, prettyPrint = true)

    @Test
    fun testValueWrapperDummyLex() {
        val serializable: Map<String, Any> = lex.toData()
        val jsonString = json.encodeToString(serializable)
        ps.println(jsonString)
    }

    @Test
    fun testValueWrapperDummySynset() {
        val serializable: Map<String, Any> = synset.toData()
        val jsonString = json.encodeToString(serializable)
        ps.println(jsonString)
    }

    @Test
    fun testValueWrapperDummySense() {
        val serializable: Map<String, Any> = sense.toData()
        val jsonString = json.encodeToString(serializable)
        ps.println(jsonString)
    }

    companion object {
        val silent = !System.getProperties().containsKey("VERBOSE") && if (System.getProperties().containsKey("SILENT")) true
        else true

        val ps: PrintStream = if (!silent) Tracing.psInfo else Tracing.psNull

        // @JvmStatic
        // @BeforeClass
        // fun init() {
        // }
    }
}