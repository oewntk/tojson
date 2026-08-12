package org.oewntk.json.out.oewn

import org.oewntk.json.out.JsonCodec
import org.oewntk.json.out.JsonMethod
import org.oewntk.json.out.Tracing
import org.oewntk.model.CoreModel
import org.oewntk.model.toOneOEWNData
import org.oewntk.model.toSplitOEWNData
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
    val fileext: String = "json",
    val generated: Boolean = false,
    jsonMethod: JsonMethod = JsonMethod.JSON_ELEMENT,
    prettyPrint: Boolean = true,
    val leaveRedundantRelation: Boolean = false,
    private val verbose: Boolean = false,
) : Consumer<CoreModel> {

    val json = JsonCodec(jsonMethod = jsonMethod, prettyPrint = prettyPrint)

    private fun jsonCoreModel(model: CoreModel, dir: File) {
        if (split) {
            model.toSplitOEWNData(generated = generated, leaveRedundantRelation = leaveRedundantRelation).forEach { (serializable, file) ->
                if (verbose) Tracing.psInfo.printf("[File] %s%n", file)
                val content = json.encodeToString(serializable)
                File(dir, "$file.$fileext").writeText(content)
            }
        } else {
            val file = File(dir, "oewn.$fileext")
            val serializables = model.toOneOEWNData(leaveRedundantRelation = leaveRedundantRelation).iterator()
            val (serializable1, _) = serializables.next()
            val (serializable2, _) = serializables.next()
            val content1 = json.encodeToString(serializable1)
            val content2 = json.encodeToString(serializable2)
            if (verbose) Tracing.psInfo.printf("[File] %s%n", file)
            file.writeText(content1 + "\n\n" + content2)
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