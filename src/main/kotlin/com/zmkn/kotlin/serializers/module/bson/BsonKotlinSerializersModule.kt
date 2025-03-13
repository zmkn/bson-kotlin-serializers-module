package com.zmkn.kotlin.serializers.module.bson

import com.zmkn.kotlin.serializers.module.bson.serializers.ObjectIdSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.SerializersModuleBuilder
import org.bson.types.ObjectId

object BsonKotlinSerializersModule {
    val objectIdSerializersModuleBuilder = fun SerializersModuleBuilder.() {
        contextual(ObjectId::class, ObjectIdSerializer)
    }

    val all: SerializersModule by lazy {
        generateModule(
            objectIdSerializersModuleBuilder,
        )
    }

    fun generateModule(vararg serializersModuleBuilder: SerializersModuleBuilder.() -> Unit): SerializersModule {
        return SerializersModule {
            serializersModuleBuilder.forEach {
                it()
            }
        }
    }
}
