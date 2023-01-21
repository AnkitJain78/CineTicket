package com.example.moviebooking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.moviebooking.R
import java.time.Month
import java.util.*

class DateAdapter(private val dateList: ArrayList<Date>) : Adapter<DateAdapter.DateViewHolder>() {
    class DateViewHolder(itemView: View) : ViewHolder(itemView) {
        var date: TextView = itemView.findViewById(R.id.date)
        var day: TextView = itemView.findViewById(R.id.day)
        var month: TextView = itemView.findViewById(R.id.month)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val currentDate = dateList[position]
        holder.date.text = currentDate.date.toString()
        holder.day.text = getDayByInt(currentDate.day)
        holder.month.text = Month.of(currentDate.month).toString().subSequence(0, 3)
    }

    private fun getDayByInt(day: Int): String {
        when (day) {
            0 -> return "Sun"
            1 -> return "Mon"
            2 -> return "Tue"
            3 -> return "Wed"
            4 -> return "Thu"
            5 -> return "Fri"
            6 -> return "Sat"
        }
        return "Sun"
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

}