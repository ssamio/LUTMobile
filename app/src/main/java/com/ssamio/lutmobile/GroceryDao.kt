package com.ssamio.lutmobile

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GroceryDao {

    @Query("SELECT * FROM grocery_items WHERE isLater = 0")
    fun getAllMainItems(): List<GroceryItem>

    @Query("SELECT * FROM grocery_items WHERE isLater = 1")
    fun getAllLaterItems(): List<GroceryItem>

    @Insert
    fun insertItem(item: GroceryItem)

    @Update
    fun updateItem(item: GroceryItem)

    @Delete
    fun deleteItem(item: GroceryItem)
}
