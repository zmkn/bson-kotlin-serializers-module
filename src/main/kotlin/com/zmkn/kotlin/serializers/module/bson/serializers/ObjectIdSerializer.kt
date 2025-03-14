package com.zmkn.kotlin.serializers.module.bson.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*
import org.bson.types.ObjectId

object ObjectIdSerializer : KSerializer<ObjectId> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ObjectIdSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ObjectId) {
        if (encoder is JsonEncoder) {
            val jsonObject = buildJsonObject {
                put("\$oid", JsonPrimitive(value.toHexString()))
            }
            encoder.encodeJsonElement(jsonObject)
        } else {
            throw IllegalStateException("This serializer can only encode to JSON.")
        }
    }

    override fun deserialize(decoder: Decoder): ObjectId {
        return if (decoder is JsonDecoder) {
            val jsonObject = decoder.decodeJsonElement().jsonObject
            val oid = jsonObject["\$oid"]?.jsonPrimitive?.content ?: throw IllegalArgumentException("Invalid ObjectId format.")
            ObjectId(oid)
        } else {
            throw IllegalStateException("This serializer can only decode from JSON")
        }
    }
}
