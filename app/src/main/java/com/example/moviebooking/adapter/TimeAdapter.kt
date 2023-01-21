package com.example.moviebooking.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.moviebooking.R
import com.example.moviebooking.SeatingActivity

class TimeAdapter(val timeList: ArrayList<String>, val context: Context) :
    Adapter<TimeAdapter.TimeViewHolder>() {
    class TimeViewHolder(itemView: View) : ViewHolder(itemView) {
        var time: TextView = itemView.findViewById(R.id.timeText)
        var linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayoutTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_time, parent, false)
        return TimeViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {
        holder.time.text = timeList[position]
        holder.linearLayout.setOnClickListener {
            //holder.linearLayout.setBackgroundResource(R.drawable.pressed_time_background)
            showDialog()
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Do you want to continue?")
        //builder.setMessage()
        // builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Yes") { _, _ ->
            val intent = Intent(context, SeatingActivity::class.java)
            context.startActivity(intent)
        }
        builder.setNegativeButton("Cancel") { dialogInterface, _ ->
            dialogInterface.cancel()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun getItemCount(): Int {
        return timeList.size
    }
}