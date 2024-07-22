package com.ssamio.lutmobile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GroceryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GroceryRepository = GroceryRepository(application)
    private val _mainListItems = MutableLiveData<List<GroceryItem>>()
    val mainListItems: LiveData<List<GroceryItem>> = _mainListItems

    private val _laterListItems = MutableLiveData<List<GroceryItem>>()
    val laterListItems: LiveData<List<GroceryItem>> = _laterListItems

    init {
        loadMainListItems()
        loadLaterListItems()
    }

    fun loadMainListItems() {
        viewModelScope.launch {
            // Load main list items from the repository
            _mainListItems.value = repository.getAllMainItems()
        }
    }

    fun loadLaterListItems() {
        viewModelScope.launch {
            // Load later list items from the repository
            _laterListItems.value = repository.getAllLaterItems()
        }
    }

    fun insertItem(item: GroceryItem) {
        viewModelScope.launch {
            // Add item to the repository and reload the list
            repository.insertItem(item)
            loadMainListItems()
        }
    }

    fun updateItem(item: GroceryItem) {
        viewModelScope.launch {
            // Update item in the repository and reload the list
            repository.updateItem(item)
            loadMainListItems()
            loadLaterListItems()
        }
    }

    fun deleteItem(item: GroceryItem) {
        viewModelScope.launch {
            // Delete item from the repository and reload the list
            repository.deleteItem(item)
            loadMainListItems()
        }
    }
}

