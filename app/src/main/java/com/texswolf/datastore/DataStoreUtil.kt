package com.texswolf.datastore

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

@SuppressLint("StaticFieldLeak")
object DataStoreUtil {
    private val Context.mDataStore: DataStore<Preferences> by preferencesDataStore(name = "appData")

    private val mContext =MyApplication.mContext

     var mUserId = -1
     var mUserName = "name"

      suspend fun getUserId() {
        mUserId =  mContext.mDataStore.data.map { preferences ->
            preferences[USER_ID] ?: 0
        }.first()
    }

     suspend fun getUserName() {
        mUserName = mContext.mDataStore.data.map { preferences ->
            preferences[USER_NAME] ?: "empty"
        }.first()

    }

     suspend fun setUserName(userName: String) {
        mContext. mDataStore.edit { settings ->
            settings[USER_NAME] = userName
        }
    }

     suspend fun setUserId(userId: Int) {
        mContext. mDataStore.edit { settings ->
            settings[USER_ID] = userId
        }
    }

    private val USER_ID = intPreferencesKey("userId")
    private val USER_NAME = stringPreferencesKey("userName")
}