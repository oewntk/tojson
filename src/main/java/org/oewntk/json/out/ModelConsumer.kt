/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

import org.oewntk.json.out.Serializer.prettyPrintModel
import org.oewntk.json.out.Serializer.serializeModel
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
class ModelConsumer(private val file: File, private val prettyPrint: Boolean = false) : Consumer<Model> {

    override fun accept(model: Model) {
        Tracing.psInfo.printf("[Model] %s%n", model.sources.contentToString())
        try {
            val jsoned = if (prettyPrint) prettyPrintModel(model) else serializeModel(model)
            file.writeText(jsoned)

        } catch (e: IOException) {
            e.printStackTrace(Tracing.psErr)
        }
    }
}
