package com.example.datastorepreference

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = DataStoreManager(application)
    val getStringText = dataStore.getStrText().asLiveData(Dispatchers.IO)

    fun setText(str : String){
        viewModelScope.launch {
            dataStore.setStrText(str)
        }
    }
}