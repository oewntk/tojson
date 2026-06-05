package org.oewntk.json.out

@kotlinx.serialization.Serializable
sealed class Value {
    @kotlinx.serialization.Serializable
    object NullValue : Value()

    @kotlinx.serialization.Serializable
    data class BoolValue(val v: Boolean) : Value()

    @kotlinx.serialization.Serializable
    data class IntValue(val v: Int) : Value()

    @kotlinx.serialization.Serializable
    data class LongValue(val v: Long) : Value()

    @kotlinx.serialization.Serializable
    data class FloatValue(val v: Float) : Value()

    @kotlinx.serialization.Serializable
    data class DoubleValue(val v: Double) : Value()

    @kotlinx.serialization.Serializable
    data class StringValue(val v: String) : Value()

    @kotlinx.serialization.Serializable
    data class ListValue(val v: List<Value>) : Value()

    @kotlinx.serialization.Serializable
    data class SetValue(val v: Set<Value>) : Value()

    @kotlinx.serialization.Serializable
    data class MapValue(val v: Map<String, Value>) : Value()
}

fun Any?.toValue(): Value = when (this) {
    null -> Value.NullValue
    is Boolean -> Value.BoolValue(this)
    is Int -> Value.IntValue(this)
    is Long -> Value.LongValue(this)
    is Float -> Value.FloatValue(this)
    is Double -> Value.DoubleValue(this)
    is Char -> Value.CharValue(this)
    is String -> Value.StringValue(this)
    is Array<*> -> Value.ListValue(this.map { it.toValue() })
    is List<*> -> Value.ListValue(this.map { it.toValue() })
    is Set<*> -> Value.SetValue(this.map { it.toValue() }.toSet())
    is Map<*, *> -> Value.MapValue(this.entries.associate { (k, v) -> (k as String) to v.toValue() })
    else -> error("Unsupported type: ${this.let { it::class }}")
}

fun Value?.fromValue(): Any = when (this) {
    Value.NullValue -> "null"
    is Value.BoolValue -> v
    is Value.IntValue -> v
    is Value.LongValue -> v
    is Value.FloatValue -> v
    is Value.DoubleValue -> v
    is Value.CharValue -> v
    is Value.StringValue -> v
    is Value.ListValue -> v.map { it.fromValue() }
    is Value.SetValue -> v.map { it.fromValue() }.toSet()
    is Value.MapValue -> v.mapValues { (_, v) -> v.fromValue() }
    else -> error("Unsupported type: ${this?.let { it::class }}")
}