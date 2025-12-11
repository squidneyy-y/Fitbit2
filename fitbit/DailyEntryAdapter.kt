package com.example.fitbit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fitbit.data.DailyEntry

class DailyEntryAdapter(
    private var entries: List<DailyEntry>,
    private val onLongClick: (DailyEntry) -> Unit
) : RecyclerView.Adapter<DailyEntryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val foodTextView: TextView = view.findViewById(R.id.tvFoodName)
        val caloriesTextView: TextView = view.findViewById(R.id.tvCalories)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_entry_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]
        holder.foodTextView.text = entry.foodName
        holder.caloriesTextView.text = "${entry.calories} Calories"

        holder.itemView.setOnLongClickListener {
            onLongClick(entry)
            true
        }
    }

    override fun getItemCount() = entries.size

    fun updateData(newEntries: List<DailyEntry>) {
        entries = newEntries
        notifyDataSetChanged()
    }
}