package com.assignment.onlinesales.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.onlinesales.R
import com.assignment.onlinesales.database.HistoryHeaderItem

class HistoryHeaderAdapter(
    private val mList: List<HistoryHeaderItem>,
    private val context: Context
) : RecyclerView.Adapter<HistoryHeaderAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view 

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_header_history, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = mList[position]

        holder.textView.text = model.date
        holder.recyclerView.layoutManager = LinearLayoutManager(context)
        holder.recyclerView.adapter = HistoryAdapter(model.dataList)

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = this.itemView.findViewById(R.id.tv_history)
        val recyclerView: RecyclerView = this.itemView.findViewById(R.id.rv_history)
    }
}