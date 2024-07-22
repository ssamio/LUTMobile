package com.ssamio.lutmobile

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LaterListActivity : AppCompatActivity() {

    private lateinit var laterListRecyclerView: RecyclerView
    private lateinit var adapter: LaterListAdapter
    private val groceryViewModel: GroceryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_later_list)

        laterListRecyclerView = findViewById(R.id.laterListRecyclerView)
        adapter = LaterListAdapter(this, mutableListOf()) { item ->
            moveToMainList(item)
        }
        laterListRecyclerView.adapter = adapter
        laterListRecyclerView.layoutManager = LinearLayoutManager(this)

        groceryViewModel.laterListItems.observe(this, Observer { items ->
            adapter.setItems(items)
        })
    }

    // Move item from later list to the main list
    private fun moveToMainList(item: GroceryItem) {
        item.isLater = false
        groceryViewModel.updateItem(item)
        setResult(Activity.RESULT_OK) // Notify MainActivity that an item was moved
    }

    // Ensure main list is updated once later list view is exited
    override fun onBackPressed() {
        setResult(Activity.RESULT_OK) // Notify MainActivity to reload data when back button is pressed
        super.onBackPressed()
    }
}

