package com.ssamio.lutmobile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LaterListAdapter(
    private val context: Context,
    private var items: MutableList<GroceryItem>,
    private val onMoveToMainList: (GroceryItem) -> Unit
) : RecyclerView.Adapter<LaterListAdapter.LaterListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaterListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.later_list_item, parent, false)
        return LaterListViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaterListViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(newItems: List<GroceryItem>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class LaterListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.itemName)
        private val itemQuantity: TextView = itemView.findViewById(R.id.itemQuantity)
        private val moveToMainListButton: Button = itemView.findViewById(R.id.moveToMainListButton)

        fun bind(item: GroceryItem) {
            itemName.text = item.name
            itemQuantity.text = "Quantity: ${item.quantity}"
            moveToMainListButton.setOnClickListener {
                onMoveToMainList(item)
            }
        }
    }
}

