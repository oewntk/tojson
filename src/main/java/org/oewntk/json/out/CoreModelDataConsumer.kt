package org.oewntk.json.out

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.oewntk.model.CoreModel
import org.oewntk.model.toData
import java.io.File
import java.io.IOException
import java.util.function.Consumer

/**
 * Main class that serializes the core model.
 *
 * @property outDir output dir
 * @author Bernard Bou
 */
class CoreModelDataConsumer(
    private val outDir: File,
    val split: Boolean = true,
    prettyPrintFlag: Boolean = true
) : Consumer<CoreModel> {

    @OptIn(ExperimentalSerializationApi::class)
    val json = Json {
        if (prettyPrintFlag) {
            prettyPrint = true
            prettyPrintIndent = "  " // default is 4 spaces
        }
    }

    private fun jsonCoreModel(model: CoreModel, dir: File) {
        val (dataLexes, dataSynsets, dataSenses) = model.toData()
        val lexContent = json.encodeToString(KSData.serializer(), KSData(dataLexes))
        val synsetContent = json.encodeToString(KSData.serializer(), KSData(dataSynsets))
        val senseContent = json.encodeToString(KSData.serializer(), KSData(dataSenses))
        if (split) {
            var file = File(dir, "oewn-synsets.json")
            Tracing.psInfo.printf("[File] %s%n", file)
            file.writeText(lexContent + synsetContent + senseContent)

            file = File(dir, "oewn-lexes.json")
            Tracing.psInfo.printf("[File] %s%n", file)
            file.writeText(lexContent + synsetContent + senseContent)

            file = File(dir, "oewn-senses.json")
            Tracing.psInfo.printf("[File] %s%n", file)
            file.writeText(lexContent + synsetContent + senseContent)
        } else {
            val file = File(dir, "oewn.json")
            Tracing.psInfo.printf("[File] %s%n", file)
            file.writeText(lexContent + "\n\n" + synsetContent + "\n\n" + senseContent)
        }
    }

    override fun accept(model: CoreModel) {
        Tracing.psInfo.printf("[CoreModel] %s%n", model.source)
        if (!outDir.exists()) {
            outDir.mkdirs()
        }
        try {
            jsonCoreModel(model, outDir)
        } catch (e: IOException) {
            e.printStackTrace(Tracing.psErr)
        }
    }
}