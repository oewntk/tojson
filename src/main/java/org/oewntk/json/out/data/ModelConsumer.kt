package org.oewntk.json.out.data

import org.oewntk.json.out.JsonCodec
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
    prettyPrintFlag: Boolean = false
) : Consumer<Model> {

    val json = JsonCodec(prettyPrintFlag = prettyPrintFlag)

    private fun yamlModel(model: Model, dir: File) {
        val frameMap = model.verbFrames.associate { it.id to it.frame }
        val frameContent = json.encodeToString(frameMap)
        val templateMap = model.verbTemplates.associate { it.id to it.template }
        val templateContent = json.encodeToString(templateMap)
        if (split) {
            val frameFile = File(dir, "frames.$fileext")
            Tracing.psInfo.printf("[File] %s%n", frameFile)
            frameFile.writeText(frameContent)
            val templateFile = File(dir, "templates.$fileext")
            Tracing.psInfo.printf("[File] %s%n", templateFile)
            templateFile.writeText(templateContent)
        } else {
            val frameAndTemplateFile = File(dir, "frames_templates.$fileext")
            Tracing.psInfo.printf("[File] %s%n", frameAndTemplateFile)
            frameAndTemplateFile.writeText(frameContent + "\n\n" + templateContent)
        }
    }

    override fun accept(model: Model) {
        Tracing.psInfo.println("[Model] ${model.sources.contentToString()}")
        if (!outDir.exists()) {
            outDir.mkdirs()
        }
        CoreModelConsumer(outDir, split = split, fileext = fileext, generated = generated).accept(model)
        try {
            yamlModel(model, outDir)
        } catch (e: IOException) {
            e.printStackTrace(Tracing.psErr)
        }
    }
}