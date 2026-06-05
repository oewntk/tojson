package org.oewntk.json.out

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import kotlinx.serialization.serializer

object AnySerializerThroughJsonElement : KSerializer<Any> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Any")

    override fun serialize(encoder: Encoder, value: Any) {
        val jsonEncoder = encoder as? JsonEncoder ?: error("AnySerializer only works with Json")
        jsonEncoder.encodeJsonElement(toJsonElement(value))
    }

    override fun deserialize(decoder: Decoder): Any {
        val jsonDecoder = decoder as? JsonDecoder ?: error("AnySerializer only works with Json")
        return fromJsonElement(jsonDecoder.decodeJsonElement())
    }

    private fun toJsonElement(value: Any?): JsonElement = when (value) {
        null -> JsonNull
        is Boolean -> JsonPrimitive(value)
        is Number -> JsonPrimitive(value)
        is String -> JsonPrimitive(value)
        is Map<*, *> -> JsonObject(
            value.entries.associate { (k, v) ->
                (k as? String ?: error("Map keys must be String")) to toJsonElement(v)
            }
        )
        is Collection<*> -> JsonArray(value.map { toJsonElement(it) })
        else -> {
            // fallback: try registered serializer
            val serializer = try {
                Json.serializersModule.serializer(value::class.java)
            } catch (_: SerializationException) {
                error("AnySerializer: unsupported type ${value::class}")
            }
            @Suppress("UNCHECKED_CAST")
            Json.encodeToJsonElement(serializer, value)
        }
    }

    private fun fromJsonElement(element: JsonElement): Any = when (element) {
        is JsonNull -> "null"          // or throw, or use Any?
        is JsonPrimitive -> element.toAnyPrimitive()
        is JsonObject -> element.jsonObject.mapValues { (_, v) -> fromJsonElement(v) }
        is JsonArray -> element.jsonArray.map { fromJsonElement(it) }
    }

    private fun JsonPrimitive.toAnyPrimitive(): Any {
        if (isString) return content
        booleanOrNull?.let { return it }
        intOrNull?.let { return it }
        longOrNull?.let { return it }
        doubleOrNull?.let { return it }
        error("Unknown primitive: $content")
    }
}
