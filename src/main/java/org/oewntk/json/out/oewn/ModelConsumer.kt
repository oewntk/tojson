package org.oewntk.json.out.oewn

import org.oewntk.json.out.JsonCodec
import org.oewntk.json.out.JsonMethod
import org.oewntk.json.out.Tracing
import org.oewntk.model.Model
import java.io.File
import java.io.IOException
import java.util.function.Consumer

/**
 * Main class that serializes the core model.
 *
 * @property outDir output dir
 * @author Bernard Bou
 */
class ModelConsumer(
    private val outDir: File,
    val split: Boolean = true,
    val fileext: String = "json",
    val generated: Boolean = false,
    val jsonMethod: JsonMethod = JsonMethod.ANY_SERIALIZER,
    val prettyPrint: Boolean = true,
    private val verbose: Boolean = false,
) : Consumer<Model> {

    val json = JsonCodec(jsonMethod = jsonMethod, prettyPrint = prettyPrint)

    private fun jsonModel(model: Model, dir: File) {
        val frameMap = model.verbFrames.associate { it.id to it.frame }
        val frameContent = json.encodeToString(frameMap)
        val frameFile = File(dir, "frames.$fileext")
        Tracing.psInfo.printf("[File] %s%n", frameFile)
        frameFile.writeText(frameContent)

        val templateMap = model.verbTemplates.associate { it.id to it.template }
        val templateContent = json.encodeToString(templateMap)
        val templateFile = File(dir, "templates.$fileext")
        Tracing.psInfo.printf("[File] %s%n", templateFile)
        templateFile.writeText(templateContent)
    }

    override fun accept(model: Model) {
        Tracing.psInfo.println("[Model] ${model.sources.contentToString()}")
        if (!outDir.exists()) {
            outDir.mkdirs()
        }
        CoreModelConsumer(outDir, split = split, fileext = fileext, generated = generated, jsonMethod = jsonMethod, prettyPrint = prettyPrint).accept(model)
        try {
            jsonModel(model, outDir)
        } catch (e: IOException) {
            e.printStackTrace(Tracing.psErr)
        }
    }
}