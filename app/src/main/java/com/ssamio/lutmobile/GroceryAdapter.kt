package com.ssamio.lutmobile

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class GroceryAdapter(
    private val context: Context,
    var items: MutableList<GroceryItem>,
    private val onItemDelete: (GroceryItem) -> Unit,
    private val onItemUpdate: (GroceryItem) -> Unit
) : RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.grocery_list_item, parent, false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: List<GroceryItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        if (position >= 0 && position < items.size) {
            val item = items[position]
            onItemDelete(item)
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun updateItem(item: GroceryItem, position: Int) {
        if (position >= 0 && position < items.size) {
            items[position] = item
            notifyItemChanged(position)
        }
    }

    inner class GroceryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.itemName)
        private val itemQuantity: TextView = itemView.findViewById(R.id.itemQuantity)

        fun bind(item: GroceryItem) {
            // Bind item data to the views
            itemName.text = item.name
            itemQuantity.text = "Quantity: ${item.quantity}"
            itemView.setBackgroundColor(context.resources.getColor(R.color.backgroundColor, null))

            // Apply strike-through effect if item is picked up
            if (item.isPickedUp) {
                itemName.paintFlags = itemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                itemName.paintFlags = itemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
            // Set a long click listener to delete the item
            itemView.setOnLongClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Delete Item")
                    .setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes") { dialog, _ ->
                        deleteItem(adapterPosition)
                        dialog.dismiss()
                    }
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                true
            }
        }
    }

}

