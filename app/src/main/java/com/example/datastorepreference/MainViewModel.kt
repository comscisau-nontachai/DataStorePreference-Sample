package com.example.datastorepreference

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = DataStoreManager(application)
    val getStringText = dataStore.getStrText().asLiveData(Dispatchers.IO)
    val getWalkMode = dataStore.getWalkMode().asLiveData(Dispatchers.IO)

    fun setText(str : String){
        viewModelScope.launch {
            dataStore.setStrText(str)
        }
    }

    fun setWalkMode(isWalk : Boolean) = viewModelScope.launch {
        dataStore.setWalkMode(isWalk)
    }
}