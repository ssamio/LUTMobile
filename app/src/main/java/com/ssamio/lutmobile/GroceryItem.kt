package com.ssamio.lutmobile

import androidx.room.Entity
import androidx.room.PrimaryKey

// Database model for grocery item
@Entity(tableName = "grocery_items")
data class GroceryItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val quantity: Int,
    var isPickedUp: Boolean = false,
    var isLater: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)
