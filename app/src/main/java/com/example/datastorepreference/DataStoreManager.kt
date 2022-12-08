package com.example.datastorepreference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreManager(context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "THEME_KEY")
    private val dataStore = context.dataStore

    companion object {
        val strText = stringPreferencesKey(name = "TEXT_KEY")
    }

    suspend fun setStrText(str: String) {
        dataStore.edit { pref ->
            pref[strText] = str
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
            val str = pref[strText] ?: "default text"
            str
        }
    }
}