package com.prashant.mongodb.db


import io.realm.kotlin.types.BaseRealmObject
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId
import kotlin.reflect.KClass


interface MongoDB {

    suspend fun add(
        value: RealmObject
    )

    suspend fun <Generic : BaseRealmObject> retrieve(
        clazz: Generic,
        objectId: ObjectId
    ): BaseRealmObject?

    suspend fun <Generic : BaseRealmObject> update(
        objectId: ObjectId,
        value: Generic,
        clazz: Generic,
    )

    suspend fun <Generic : BaseRealmObject> delete(
        clazz: Generic,
        objectId: ObjectId,
    )

}