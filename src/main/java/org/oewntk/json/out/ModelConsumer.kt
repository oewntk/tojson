/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.oewntk.model.DataModel
import org.oewntk.model.Model
import java.io.File
import java.io.IOException
import java.util.function.Consumer

/**
 * Main class that serializes the model
 *
 * @property file output file
 * @param prettyPrintFlag pretty print output
 * @author Bernard Bou
 */
class ModelConsumer(private val file: File, prettyPrintFlag: Boolean = false) : Consumer<Model> {

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        if (prettyPrintFlag) {
            prettyPrint = true
            prettyPrintIndent = "  " // default is 4 spaces
        }
    }

    private fun serializeCoreModel(model: Model, file: File) {
        val jsonString = json.encodeToString(DataModel(model))
        println(jsonString)
        file.writeText(jsonString)
        // throw NotImplementedError()
    }

    override fun accept(model: Model) {
        Tracing.psInfo.printf("[Model] %s%n", model.sources.contentToString())
        try {
            serializeCoreModel(model, file)
        } catch (e: IOException) {
            e.printStackTrace(Tracing.psErr)
        }
    }
}
