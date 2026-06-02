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

    fun <T: Map<String,Any>> encodeToString(value: T): String {
        return delegate.encodeToString(SerializableWrapper.serializer(), SerializableWrapper(value))
    }
}