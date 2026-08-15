package org.oewntk.json.out.data

import org.oewntk.json.out.JsonCodec
import org.oewntk.json.out.JsonMethod
import org.oewntk.json.out.Tracing
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
class CoreModelConsumer(
    private val outDir: File,
    val split: Boolean = true,
    val bag: Boolean = false,
    val fileext: String = "json",
    jsonMethod: JsonMethod = JsonMethod.JSON_ELEMENT,
    prettyPrint: Boolean = true,
    private val verbose: Boolean = false,
) : Consumer<CoreModel> {

    val json = JsonCodec(jsonMethod = jsonMethod, prettyPrint = prettyPrint)

    private fun jsonCoreModel(model: CoreModel, dir: File) {
        val (dataLexes, dataSynsets, dataSenses) = model.toData()

        if (split) {
            val lexContent = json.encodeToString(dataLexes)
            val synsetContent = json.encodeToString(dataSynsets)
            val senseContent = json.encodeToString(dataSenses)
            var file = File(dir, "oewn-lexes.$fileext")
            if (verbose) Tracing.psInfo.printf("[File] %s%n", file)
            file.writeText(lexContent)

            file = File(dir, "oewn-synsets.$fileext")
            if (verbose) Tracing.psInfo.printf("[File] %s%n", file)
            file.writeText(synsetContent)

            file = File(dir, "oewn-senses.$fileext")
            if (verbose) Tracing.psInfo.printf("[File] %s%n", file)
            file.writeText(senseContent)
        } else {
            val data = if (bag) dataLexes + dataSynsets + dataSenses else
                mapOf(
                    "lexes" to dataLexes,
                    "synsets" to dataSynsets,
                    "senses" to dataSenses
                )
            val content = json.encodeToString(data)
            val file = File(dir, "oewn.$fileext")
            if (verbose) Tracing.psInfo.printf("[File] %s%n", file)
            file.writeText(content)
        }
    }

    override fun accept(model: CoreModel) {
        if (verbose) Tracing.psInfo.printf("[CoreModel] %s%n", model.source)
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