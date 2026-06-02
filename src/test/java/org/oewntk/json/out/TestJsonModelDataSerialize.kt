/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import org.junit.BeforeClass
import org.junit.Test
import org.oewntk.model.LibModelSubset.subset
import org.oewntk.model.lexesDataSerialize
import org.oewntk.model.sensesDataSerialize
import org.oewntk.model.synsetsDataSerialize
import org.oewntk.ser.`in`.LibTestsSerCommon.checkOrig
import org.oewntk.ser.`in`.LibTestsSerCommon.model
import org.oewntk.ser.`in`.LibTestsSerCommon.ps

object AnySerializer : KSerializer<Any> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Any")

    override fun serialize(encoder: Encoder, value: Any) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: throw IllegalStateException("This serializer only works with JSON")

        // Convert the arbitrary runtime object into a proper JsonElement recursively
        jsonEncoder.encodeJsonElement(value.toJsonElement())
    }

    override fun deserialize(decoder: Decoder): Any {
        val jsonDecoder = decoder as? JsonDecoder
            ?: throw IllegalStateException("This serializer only works with JSON")

        // Convert JsonElement back to native Kotlin types (Maps, Lists, Primitives)
        return jsonDecoder.decodeJsonElement().toNativeValue() ?: "Null"
    }

    // Helper to recursively transform Any into JsonElement
    private fun Any?.toJsonElement(): JsonElement {
        return when (this) {
            null -> JsonNull
            is Map<*, *> -> buildJsonObject {
                forEach { (key, value) ->
                    put(key.toString(), value.toJsonElement())
                }
            }
            is List<*> -> buildJsonArray {
                forEach { add(it.toJsonElement()) }
            }
            is Number -> JsonPrimitive(this)
            is Boolean -> JsonPrimitive(this)
            is String -> JsonPrimitive(this)
            else -> JsonPrimitive(this.toString()) // Fallback
        }
    }

    // Helper to turn JsonElements back into standard Kotlin types
    private fun JsonElement.toNativeValue(): Any? {
        return when (this) {
            is JsonNull -> null
            is JsonObject -> mapValues { it.value.toNativeValue() }
            is JsonArray -> map { it.toNativeValue() }
            is JsonPrimitive -> {
                if (isString) content
                else content.toBooleanStrictOrNull()
                    ?: content.toIntOrNull()
                    ?: content.toLongOrNull()
                    ?: content.toDoubleOrNull()
                    ?: content
            }
        }
    }
}

class TestJsonModelDataSerialize {

    @kotlinx.serialization.Serializable
    data class KSData(val sdata: Map<String, @kotlinx.serialization.Serializable(with = AnySerializer::class) Any>)

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        prettyPrint = true
        prettyPrintIndent = "  " // Optional: Customize indentation (default is 4 spaces)
    }

    @Test
    fun testModelSerialization() {
        val (someLexes, someSynsets, someSenses) = model.subset()
        val dataLexes = KSData(someLexes.lexesDataSerialize())
        val dataSynsets = KSData(someSynsets.synsetsDataSerialize())
        val dataSenses = KSData(someSenses.sensesDataSerialize())
        val jsonLexesString = json.encodeToString(KSData.serializer(), dataLexes)
        val jsonSynsetsString = json.encodeToString(KSData.serializer(), dataSynsets)
        val jsonSensesString = json.encodeToString(KSData.serializer(), dataSenses)
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
