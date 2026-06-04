package org.oewntk.json.out.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.oewntk.json.out.Tracing
import org.oewntk.model.DataModel
import org.oewntk.model.Model
import java.io.File
import java.io.IOException
import java.util.function.Consumer

/**
 * Main class that serializes the model
 *
 * @property file output file
 * @param prettyPrint pretty print output
 * @author Bernard Bou
 */
class ModelConsumer(private val file: File, prettyPrint: Boolean = false) : Consumer<Model> {

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        if (prettyPrint) {
            this.prettyPrint = true
            prettyPrintIndent = "  " // default is 4 spaces
        }
    }

    private fun serializeCoreModel(model: Model, file: File) {
        val jsonString = json.encodeToString(DataModel(model))
        file.writeText(jsonString)
    }

    override fun accept(model: Model) {
        Tracing.psInfo.printf("[Model] %s%n", model.sources.contentToString())
        val outDir =  file.parentFile
        if (!outDir.exists()) {
            outDir.mkdirs()
        }
        try {
            serializeCoreModel(model, file)
        } catch (e: IOException) {
            e.printStackTrace(Tracing.psErr)
        }
    }
}