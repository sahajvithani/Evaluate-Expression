package com.assignment.onlinesales.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.assignment.onlinesales.R
import com.assignment.onlinesales.api.APIClient
import com.assignment.onlinesales.api.ApiInterface
import com.assignment.onlinesales.database.HistoryDatabaseHelper
import com.assignment.onlinesales.database.HistoryItem
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    companion object{
        const val apiHeader = "dee69bd0efmshdec99356909e2a9p1d0a1fjsn4cf56dda2ffc"
    }

    private lateinit var expressionInput: EditText
    private lateinit var resultsTextView: TextView
    var res : String = ""
    private lateinit var databaseHandler : HistoryDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getColor(R.color.blue)
        setContentView(R.layout.activity_main)

        expressionInput = findViewById(R.id.expression_input)
        resultsTextView = findViewById(R.id.results_textview)

        databaseHandler = HistoryDatabaseHelper(this@MainActivity)

        val evaluateButton = findViewById<Button>(R.id.evaluate_button)
        evaluateButton.setOnClickListener {
            res = ""
            val expressions = expressionInput.text.toString().split("\n")

            // Evaluate the expressions using the Web API
            expressions.map { expression ->
                CoroutineScope(Dispatchers.IO).async {

                    val api: ApiInterface = APIClient.client!!.create(ApiInterface::class.java)
                    val call: retrofit2.Call<String?>? = api.getData(apiHeader,expression)
                    call?.enqueue(object : retrofit2.Callback<String?> {
                        override fun onResponse(
                            call: retrofit2.Call<String?>,
                            response: retrofit2.Response<String?>,
                        ) {
                            if (response.isSuccessful) {
                                val evaluation = response.body().toString()

                                res += "$expression => $evaluation\n"

                                // Display the results
                                GlobalScope.launch(Dispatchers.Main) {
                                    resultsTextView.visibility = View.VISIBLE
                                    resultsTextView.text = res

                                    // Inserting data in database
                                    if (evaluation.isNotEmpty()) {
                                        val newItem = HistoryItem(
                                            id = 0,
                                            itemExpression = "$expression => $evaluation\n",
                                            itemDate = getCurrentDate()
                                        )
                                        databaseHandler.addHistoryItem(newItem)
                                    }
                                }
                            }
                        }
                        override fun onFailure(call: retrofit2.Call<String?>, t: Throwable) {
                            Log.e("TAG", "onFailure: ${t.message}")
                        }
                    })
                }
            }

            expressionInput.text.clear()
        }

        val ivHistory = findViewById<ImageView>(R.id.iv_history)
        ivHistory.setOnClickListener {
            startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
        }
    }

    private fun getCurrentDate(): String {
        return SimpleDateFormat("dd/MM/yyyy").format(Date())
    }

}