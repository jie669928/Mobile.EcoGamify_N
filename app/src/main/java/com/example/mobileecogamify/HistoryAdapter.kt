package com.example.mobileecogamify

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class HistoryAdapter(private val context: Context, private val historyData: List<OceanEventData>) : BaseAdapter() {

    override fun getCount(): Int {
        return historyData.size
    }

    override fun getItem(position: Int): Any {
        return historyData[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_history, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val historyItem = historyData[position]

        viewHolder.totalGarbageTextView.text = historyItem.totalGarbage
        viewHolder.currentTimeTextView.text = historyItem.currentTime
        return view
    }


    private class ViewHolder(view: View) {
        val totalGarbageTextView: TextView = view.findViewById(R.id.total_number_item)
        val currentTimeTextView: TextView = view.findViewById(R.id.timerTextView)
    }
}
