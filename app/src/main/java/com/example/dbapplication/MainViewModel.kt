package com.example.dbapplication

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.dbapplication.data.MainDB
import com.example.dbapplication.data.NameEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
class MainViewModel(val database: MainDB) : ViewModel() {
    val itemsList = database.dao.selectItems()
    var inputState = mutableStateOf("")
    var textEntity: NameEntity? = null

    fun insertItem() = viewModelScope.launch {
        try {
            val textItem = textEntity?.copy(text = inputState.value)
                ?: NameEntity(text = inputState.value)
            withContext(Dispatchers.IO) {
                database.dao.insertItem(textItem)
            }
            textEntity = null
            inputState.value = ""
        } catch (e: Exception) {
            Log.d("InsertException", e.toString())
        }
    }

    fun deleteItem(item: NameEntity) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            database.dao.deleteItem(item)
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return MainViewModel(database) as T
            }
        }
    }
}