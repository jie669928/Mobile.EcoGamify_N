package com.example.mobileecogamify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.ImageButton
import android.widget.ListView
import com.google.firebase.firestore.FirebaseFirestore
class History : AppCompatActivity() {

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyData: List<OceanEventData>
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // 获取对 ListView 的引用
        listView = findViewById<ListView>(R.id.historyListView) // 移除val关键字

        historyData = fetchHistoryData()

        // 初始化适配器
        historyAdapter = HistoryAdapter(this, historyData)

        // 将适配器与 ListView 关联
        listView.adapter = historyAdapter



    }


    private fun fetchHistoryData(): List<OceanEventData> {
        val historyData = mutableListOf<OceanEventData>()

        val db = FirebaseFirestore.getInstance()
        db.collection("ocean_event_data")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val totalGarbage = document.getString("totalGarbage")
                    val currentTime = document.getString("currentTime")

                    if (totalGarbage != null && currentTime != null) {
                        val eventData = OceanEventData(totalGarbage, currentTime)
                        historyData.add(eventData)
                    }
                }

                // 数据获取完成后，初始化适配器并将其与 ListView 关联
                historyAdapter = HistoryAdapter(this, historyData)
                listView.adapter = historyAdapter

                // 通知适配器数据已更改
                historyAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting history data", exception)
            }

        return historyData
    }
}
