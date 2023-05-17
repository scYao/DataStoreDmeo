package com.texswolf.datastore

import android.app.Application
import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import kotlin.properties.Delegates

class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        mApplicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        mContext = applicationContext

        initDb()
    }


    private fun initDb() {

        runBlocking {
            //此处取都是取第一个值，可以写两个取值操作，
            DataStoreUtil.getUserId()
            DataStoreUtil. getUserName()

            //如果是没有调用flow.first()方法，无法取到username,可以把取值放在不同协程中，Flow就不会相互干扰了
//            launch {
//                getUserId()
//            }
//            launch {
//                getUserName()
//            }
        }

//        mApplicationScope.launch {
//
//        }

        Log.e(TAG, "initDb: "+DataStoreUtil.mUserId )
        Log.e(TAG, "initDb: "+DataStoreUtil.mUserName)

    }

//    private suspend fun getUserId() {
//        mDataStore.data.first { preferences ->
//            mUserId = preferences[USER_ID] ?: 0
//            true
//        }
//    }
//
//    private suspend fun getUserName() {
//        mDataStore.data.first { preferences ->
//            mUserName = preferences[USER_NAME] ?: "empty"
//            true
//        }
//    }


    companion object {
        var mContext: Context by Delegates.notNull()
        var mApplicationScope: CoroutineScope by Delegates.notNull()
        private const val TAG = "MyApplication"
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mApplicationScope.cancel()
    }


}