package com.example.datastorepreference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreManager(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "THEME_KEY")
    private val dataStore = context.dataStore

    companion object {
        val strTextKey = stringPreferencesKey(name = "TEXT_KEY")
        val walkModeKey = booleanPreferencesKey(name = "WALK_KEY")
    }

    suspend fun setStrText(str: String) {
        dataStore.edit { pref ->
            pref[strTextKey] = str
        }
    }

    suspend fun setWalkMode(isWalk: Boolean) {
        dataStore.edit { pref ->
            pref[walkModeKey] = isWalk
        }
    }

    fun getStrText(): Flow<String> {
        return dataStore.data.catch { e ->
            if (e is IOException) {
                emit(emptyPreferences())
            } else {
                throw e
            }
        }.map { pref ->
            val str = pref[strTextKey] ?: "default text"
            str
        }
    }

    fun getWalkMode() = dataStore.data.catch { e ->
        if(e is IOException){
            emit(emptyPreferences())
        }else{
            throw e
        }
    }.map { pref ->
        val data = pref[walkModeKey] ?: true
        data
    }
}