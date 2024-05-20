/*
 * Copyright (c) 2021-2024. Bernard Bou.
 */
package org.oewntk.json.out

import org.oewntk.json.out.Serializer.prettyPrintCoreModel
import org.oewntk.json.out.Serializer.serializeCoreModel
import org.oewntk.model.CoreModel
import java.io.File
import java.io.IOException
import java.util.function.Consumer

/**
 * Main class that serializes the core model.
 *
 * @property file output file
 * @author Bernard Bou
 */
class CoreModelConsumer(private val file: File, private val prettyPrint: Boolean = false) : Consumer<CoreModel> {

    override fun accept(model: CoreModel) {
        Tracing.psInfo.printf("[CoreModel] %s%n", model.source)
        try {
            val jsoned = if (prettyPrint) prettyPrintCoreModel(model) else serializeCoreModel(model)
            file.writeText(jsoned)

        } catch (e: IOException) {
            e.printStackTrace(Tracing.psErr)
        }
    }
}
