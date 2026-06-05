package org.oewntk.json.out

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

enum class JsonMethod {
    ANY_SERIALIZER,
    JSON_ELEMENT,
    VALUE_WRAPPER,
}

class JsonCodec(prettyPrint: Boolean = true, val jsonMethod: JsonMethod = JsonMethod.ANY_SERIALIZER) {

    @OptIn(ExperimentalSerializationApi::class)
    val delegate = Json {
        if (prettyPrint) {
            this.prettyPrint = true
            prettyPrintIndent = "  " // default is 4 spaces
        }
    }

    fun encodeToString(value: Any): String {
        return when (jsonMethod) {
            JsonMethod.ANY_SERIALIZER -> delegate.encodeToString(SerializableWrapper.serializer(), SerializableWrapper(value))
            JsonMethod.JSON_ELEMENT -> delegate.encodeToString(AnySerializerThroughJsonElement, value)
            JsonMethod.VALUE_WRAPPER -> delegate.encodeToString(value.toValue())
        }
    }

    fun decodeString(str: String): Any {
        return when (jsonMethod) {
            JsonMethod.ANY_SERIALIZER -> delegate.decodeFromString(SerializableWrapper.serializer(), str).data
            JsonMethod.JSON_ELEMENT -> delegate.decodeFromString(AnySerializerThroughJsonElement, str)
            JsonMethod.VALUE_WRAPPER -> delegate.decodeFromString<Value>(str).fromValue()
        }
    }
}