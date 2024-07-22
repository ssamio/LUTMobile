package com.ssamio.lutmobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssamio.lutmobile.ui.theme.Colors

class MainActivity : AppCompatActivity() {

    private lateinit var groceryRecyclerView: RecyclerView
    private lateinit var addItemButton: Button
    private lateinit var viewLaterListButton: Button
    private lateinit var adapter: GroceryAdapter
    private lateinit var groceryViewModel: GroceryViewModel

    // Result launcher for returning from the Later List activity
    private val laterListActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            groceryViewModel.loadMainListItems() // Refresh the main list
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Colors.init(this) // Initialize color theme
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "onCreate called")

        // Initialize views
        groceryRecyclerView = findViewById(R.id.groceryRecyclerView)
        addItemButton = findViewById(R.id.addItemButton)
        viewLaterListButton = findViewById(R.id.viewLaterListButton)

        // Initialize the adapter with delete and update callbacks
        adapter = GroceryAdapter(this, mutableListOf(),
            { item -> groceryViewModel.deleteItem(item) }, // Delete callback
            { item -> groceryViewModel.updateItem(item) }  // Update callback
        )
        groceryRecyclerView.adapter = adapter
        groceryRecyclerView.layoutManager = LinearLayoutManager(this)

        // Handle swipe actions for RecyclerView items
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = adapter.items[position]
                    when (direction) {
                        ItemTouchHelper.RIGHT -> {
                            item.isPickedUp = !item.isPickedUp // Toggle picked up status
                            adapter.updateItem(item, position)
                        }
                        ItemTouchHelper.LEFT -> {
                            item.isLater = true // Mark item as later
                            adapter.updateItem(item, position)
                        }
                    }
                    groceryViewModel.updateItem(item)
                }
            }
        }

        // Attach ItemTouchHelper to RecyclerView
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(groceryRecyclerView)

        // Initialize ViewModel and observe changes
        groceryViewModel = ViewModelProvider(this).get(GroceryViewModel::class.java)
        groceryViewModel.mainListItems.observe(this, Observer { items ->
            adapter.updateItems(items)
        })

        // Set click listeners for buttons
        addItemButton.setOnClickListener {
            showAddItemDialog()
        }

        viewLaterListButton.setOnClickListener {
            val intent = Intent(this, LaterListActivity::class.java)
            laterListActivityResultLauncher.launch(intent)
        }
    }

    // Show dialog to add a new item to the grocery list
    private fun showAddItemDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_item, null)
        val itemNameEditText = dialogView.findViewById<EditText>(R.id.itemNameEditText)
        val itemQuantityEditText = dialogView.findViewById<EditText>(R.id.itemQuantityEditText)
        val addItemDialogButton = dialogView.findViewById<Button>(R.id.addItemDialogButton)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        addItemDialogButton.setOnClickListener {
            val itemName = itemNameEditText.text.toString()
            val itemQuantity = itemQuantityEditText.text.toString().toIntOrNull() ?: 0

            if (itemName.isNotEmpty() && itemQuantity > 0) {
                val newItem = GroceryItem(name = itemName, quantity = itemQuantity)
                groceryViewModel.insertItem(newItem)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please enter valid item details", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}


