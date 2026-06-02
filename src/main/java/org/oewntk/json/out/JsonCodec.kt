package org.oewntk.json.out

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

class JsonCodec(prettyPrintFlag: Boolean = true) {

    @OptIn(ExperimentalSerializationApi::class)
    val delegate = Json {
        if (prettyPrintFlag) {
            prettyPrint = true
            prettyPrintIndent = "  " // default is 4 spaces
        }
    }

    fun encodeToString(value: Any): String {
        return delegate.encodeToString(SerializableWrapper.serializer(), SerializableWrapper(value))
    }

    fun decodeString(str: String): Any {
        return delegate.decodeFromString(SerializableWrapper.serializer(), str).data
    }
}