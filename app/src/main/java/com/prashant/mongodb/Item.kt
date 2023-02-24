package com.prashant.mongodb

import org.mongodb.kbson.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Item() : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var isComplete: Boolean = false
    var summary: String = ""
    var ownerId: String = ""

    constructor(ownerId: String = "") : this() {
        this.ownerId = ownerId
    }
}