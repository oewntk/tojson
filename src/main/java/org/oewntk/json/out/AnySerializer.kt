package org.oewntk.json.out

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlin.collections.map
import kotlin.collections.mapValues

@kotlinx.serialization.Serializable
data class SerializableWrapper(val data: @kotlinx.serialization.Serializable(with = AnySerializer::class) Any)

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
            is Array<*> -> buildJsonArray {
                forEach { add(it.toJsonElement()) }
            }
            is Set<*> -> buildJsonArray {
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
