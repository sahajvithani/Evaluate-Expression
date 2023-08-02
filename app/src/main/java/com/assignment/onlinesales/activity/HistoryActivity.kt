package com.assignment.onlinesales.activity

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assignment.onlinesales.adapter.HistoryHeaderAdapter
import com.assignment.onlinesales.R
import com.assignment.onlinesales.database.HistoryDatabaseHelper
import com.assignment.onlinesales.database.HistoryHeaderItem

class HistoryActivity : AppCompatActivity() {

    private lateinit var databaseHandler : HistoryDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getColor(R.color.blue)
        setContentView(R.layout.activity_history)

        databaseHandler = HistoryDatabaseHelper(this@HistoryActivity)

        val ivBack : ImageView = findViewById(R.id.iv_back)
        ivBack.setOnClickListener {
            onBackPressed()
        }

        // Fetch all history items
        val allItems = databaseHandler.getAllHistoryItems()

        if (allItems.isNotEmpty()){
            val groupedData = allItems.groupBy { it.itemDate }
                .map { (date, items) ->
                    HistoryHeaderItem(date, items.map { it.itemExpression })
                }

            val recyclerView: RecyclerView = findViewById(R.id.rv_history)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = HistoryHeaderAdapter(groupedData, this@HistoryActivity)
        }
    }
}