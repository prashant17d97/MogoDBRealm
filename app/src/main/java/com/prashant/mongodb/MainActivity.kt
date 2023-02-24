package com.prashant.mongodb

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.prashant.mongodb.db.MongoDBImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mongoDBImpl = MongoDBImpl()
        CoroutineScope(Dispatchers.Main).launch {
            mongoDBImpl.add(Item().apply {
                summary = "Do you love"
            })
            mongoDBImpl.add(Item2().apply {
                name = "Prashant Kumar Singh"
            })


            val item=mongoDBImpl.retrieveAll<Item2>()
            item.collect{
                it.forEach {item->
                    Log.e(TAG, "onCreate: Item:${item.name},  ${item._id}")
                }
            }
        }

    }
}

