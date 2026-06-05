package org.oewntk.json.out

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

enum class Method {
    ANY_SERIALIZER,
    THROUGH_JSON_ELEMENT,
    VALUE_WRAPPER,
}

class JsonCodec(prettyPrint: Boolean = true, val method: Method = Method.ANY_SERIALIZER) {

    @OptIn(ExperimentalSerializationApi::class)
    val delegate = Json {
        if (prettyPrint) {
            this.prettyPrint = true
            prettyPrintIndent = "  " // default is 4 spaces
        }
    }

    fun encodeToString(value: Any): String {
        return when (method) {
            Method.ANY_SERIALIZER -> delegate.encodeToString(SerializableWrapper.serializer(), SerializableWrapper(value))
            Method.THROUGH_JSON_ELEMENT -> delegate.encodeToString(AnySerializerThroughJsonElement, value)
            Method.VALUE_WRAPPER -> delegate.encodeToString(value.toValue())
        }
    }

    fun decodeString(str: String): Any {
        return when (method) {
            Method.ANY_SERIALIZER -> delegate.decodeFromString(SerializableWrapper.serializer(), str).data
            Method.THROUGH_JSON_ELEMENT -> delegate.decodeFromString(AnySerializerThroughJsonElement, str)
            Method.VALUE_WRAPPER -> delegate.decodeFromString<Value>(str).fromValue()
        }
    }
}