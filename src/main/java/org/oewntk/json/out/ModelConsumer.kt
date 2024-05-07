/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

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
 * @author Bernard Bou
 */
class ModelConsumer(private val file: File) : Consumer<Model> {

    private fun serializeCoreModel(model: Model, file: File) {
        val jsonString = Json.encodeToString(DataModel(model))
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
