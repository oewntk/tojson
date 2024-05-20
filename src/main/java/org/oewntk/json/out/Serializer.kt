/*
 * Copyright (c) 2024. Bernard Bou.
 */

package org.oewntk.json.out

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.oewntk.model.*

object Serializer {

    fun serializeCoreModel(model: CoreModel): String {
        return Json.encodeToString(DataCoreModel(model))
    }

    fun serializeModel(model: Model): String {
        return Json.encodeToString(DataModel(model))
    }

    fun prettyPrintCoreModel(model: CoreModel): String {
        val prettyPrinter = Json {
            prettyPrint = true
        }
        val serialized = DataCoreModel(model)
        val serializer = DataCoreModel.serializer()
        return prettyPrinter.encodeToString(serializer, serialized)
    }

    fun prettyPrintModel(model: Model): String {
        val prettyPrinter = Json {
            prettyPrint = true
        }
        val serialized = DataModel(model)
        val serializer = DataModel.serializer()
        return prettyPrinter.encodeToString(serializer, serialized)
    }

    fun prettyPrintSynset(synset: Synset): String {
        val prettyPrinter = Json {
            prettyPrint = true
        }
        return prettyPrinter.encodeToString(Synset.serializer(), synset)
    }

    fun prettyPrintSense(sense: Sense): String {
        val prettyPrinter = Json {
            prettyPrint = true
        }
        return prettyPrinter.encodeToString(Sense.serializer(), sense)
    }

    fun prettyPrintLex(lex: Lex): String {
        val prettyPrinter = Json {
            prettyPrint = true
        }
        return prettyPrinter.encodeToString(Lex.serializer(), lex)
    }

    fun prettyPrintPronunciation(pronunciation: Pronunciation): String {
        val prettyPrinter = Json {
            prettyPrint = true
        }
        return prettyPrinter.encodeToString(Pronunciation.serializer(), pronunciation)
    }
}