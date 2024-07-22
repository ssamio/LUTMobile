package com.ssamio.lutmobile

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GroceryRepository(context: Context) {

    private val db = GroceryDatabase.getDatabase(context)
    private val groceryDao = db.groceryDao()

    suspend fun getAllMainItems(): List<GroceryItem> {
        // Fetch all main items from the database
        return withContext(Dispatchers.IO) {
            groceryDao.getAllMainItems()
        }
    }

    suspend fun getAllLaterItems(): List<GroceryItem> {
        // Fetch all later items from the database
        return withContext(Dispatchers.IO) {
            groceryDao.getAllLaterItems()
        }
    }

    suspend fun insertItem(item: GroceryItem) {
        // Insert a new item into the database
        withContext(Dispatchers.IO) {
            groceryDao.insertItem(item)
        }
    }

    suspend fun updateItem(item: GroceryItem) {
        // Update an existing item in the database
        withContext(Dispatchers.IO) {
            groceryDao.updateItem(item)
        }
    }

    suspend fun deleteItem(item: GroceryItem) {
        // Delete an item from the database
        withContext(Dispatchers.IO) {
            groceryDao.deleteItem(item)
        }
    }
}

