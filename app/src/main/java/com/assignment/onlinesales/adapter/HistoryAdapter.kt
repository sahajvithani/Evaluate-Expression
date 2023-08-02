package com.assignment.onlinesales.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assignment.onlinesales.R

class HistoryAdapter(private val mList: List<String>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
  
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }
  
    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
  
        // sets the text to the textview from our itemHolder class
        holder.textView.text = mList[position]
    }
  
    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }
  
    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.tv_history)
    }
}