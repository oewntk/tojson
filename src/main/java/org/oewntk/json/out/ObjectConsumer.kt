package org.oewntk.json.out

import org.oewntk.model.CoreModel
import org.oewntk.model.SerializationMode
import java.io.PrintStream
import java.util.function.BiConsumer

/**
 * Class that serializes an object to a YAML string
 *
 * @author Bernard Bou
 */
open class ObjectTransformer(
    val mode: SerializationMode = SerializationMode.DATA,
    jsonMethod: JsonMethod = JsonMethod.ANY_SERIALIZER,
    prettyPrint: Boolean = true,
    val leaveRedundantRelation: Boolean = false,

    ) : (Any, CoreModel) -> String {

    val json = JsonCodec(jsonMethod = jsonMethod, prettyPrint = prettyPrint)

    override fun invoke(obj: Any, model: CoreModel): String {

        val serializable = mode.serialize(obj, model.senseResolver, leaveRedundantRelation = leaveRedundantRelation)
        return json.encodeToString(serializable)
    }
}

open class ObjectConsumer(
    val ps: PrintStream,
    mode: SerializationMode = SerializationMode.DATA,
    jsonMethod: JsonMethod = JsonMethod.ANY_SERIALIZER,
    prettyPrint: Boolean = true,
    leaveRedundantRelation: Boolean = false,
) : ObjectTransformer(mode = mode, jsonMethod = jsonMethod, prettyPrint = prettyPrint, leaveRedundantRelation = leaveRedundantRelation), BiConsumer<Any, CoreModel> {

    override fun accept(obj: Any, model: CoreModel) {
        val str = super.invoke(obj, model)
        ps.println(str)
    }
}
