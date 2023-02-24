package com.prashant.mongodb.db

import android.util.Log
import com.prashant.mongodb.Item
import com.prashant.mongodb.Item2
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.BaseRealmObject
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId
import kotlin.reflect.KClass

class MongoDBImpl : MongoDB {
    private val config = RealmConfiguration.create(schema = setOf(Item::class, Item2::class))
    val realm: Realm = Realm.open(config)


    override suspend fun add(value: RealmObject) {
        realm.write {
            this.copyToRealm(instance = value)
        }
    }

    override suspend fun <Generic : BaseRealmObject> retrieve(
        clazz: Generic,
        objectId: ObjectId
    ): BaseRealmObject? {
        return realm.query(clazz::class, query = "_id ==$0", objectId).first().find()
    }

    override suspend fun <Generic : BaseRealmObject> update(
        objectId: ObjectId,
        value: Generic,
        clazz: Generic
    ) {
        var queryData = retrieve(clazz,objectId)
        if (queryData!=null){
            realm.write {
                queryData = value
            }
        }else{
            Log.e(TAG, "update: Data against the objectId: $objectId is not available.", )
        }
    }

    override suspend fun <Generic : BaseRealmObject> delete(clazz: Generic, objectId: ObjectId) {
        val objectIdData = retrieve(clazz, objectId)
        if (objectIdData != null) {
            realm.write {
                try {
                    delete(objectIdData)
                } catch (e: java.lang.Exception) {
                    Log.e(TAG, "delete: ${e.message}")
                }
            }
        }
    }

    inline fun <reified Generic : BaseRealmObject> retrieveAll(): Flow<List<Generic>> {
        return realm.query(Generic::class).asFlow().map { it.list }
    }


    companion object {
        const val TAG = "MongoDBImpl"
    }

}