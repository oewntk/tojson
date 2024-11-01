/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

import kotlinx.serialization.ExperimentalSerializationApi
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
 * @param prettyPrintFlag pretty print output
 * @author Bernard Bou
 */
@Suppress("unused")
class CoreModelConsumer(private val file: File, prettyPrintFlag: Boolean = false) : Consumer<CoreModel> {

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        if (prettyPrintFlag) {
            prettyPrint = true
            prettyPrintIndent = "  " // default is 4 spaces
        }
    }

    private fun serializeCoreModel(model: CoreModel, file: File) {
        val jsonString = json.encodeToString(DataCoreModel(model))
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
