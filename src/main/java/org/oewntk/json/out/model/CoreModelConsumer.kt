package org.oewntk.json.out.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.oewntk.json.out.Tracing
import org.oewntk.model.CoreModel
import org.oewntk.model.DataCoreModel
import java.io.File
import java.io.IOException
import java.util.function.Consumer

/**
 * Main class that serializes the core model.
 *
 * @property file output file
 * @param prettyPrint pretty print output
 * @author Bernard Bou
 */
class CoreModelConsumer(
    private val file: File,
    prettyPrint: Boolean = false,
    private val verbose: Boolean = false,
) : Consumer<CoreModel> {

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        if (prettyPrint) {
            this.prettyPrint = true
            prettyPrintIndent = "  " // default is 4 spaces
        }
    }

    private fun serializeCoreModel(model: CoreModel, file: File) {
        val jsonString = json.encodeToString(DataCoreModel(model))
        file.writeText(jsonString)
    }

    override fun accept(model: CoreModel) {
        Tracing.psInfo.printf("[CoreModel] %s%n", model.source)
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