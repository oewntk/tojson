/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.oewntk.model.CoreModel
import org.oewntk.model.DataCoreModel
import java.io.File
import java.io.IOException
import java.util.function.Consumer

/**
 * Main class that serializes the core model.
 *
 * @property file output file
 * @author Bernard Bou
 */
class CoreModelConsumer(private val file: File) : Consumer<CoreModel> {

    private fun serializeCoreModel(model: CoreModel, file: File) {
        val jsonString = Json.encodeToString(DataCoreModel(model))
        println(jsonString)
        file.writeText(jsonString)
        //throw NotImplementedError()
    }

    override fun accept(model: CoreModel) {
        Tracing.psInfo.printf("[CoreModel] %s%n", model.source)
        try {
            serializeCoreModel(model, file)
        } catch (e: IOException) {
            e.printStackTrace(Tracing.psErr)
        }
    }
}
