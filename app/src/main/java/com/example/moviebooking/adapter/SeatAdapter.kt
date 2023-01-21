package com.example.moviebooking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebooking.R

class SeatAdapter(
    private val noOfSeats: Int, val context: Context, val recyclerView: RecyclerView,
    private var i: Int = 1
) : RecyclerView.Adapter<SeatAdapter.SeatViewHolder>() {
    class SeatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var seatNo: TextView = itemView.findViewById(R.id.seat_no)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_seat, parent, false)
        return SeatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noOfSeats
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.seatNo.text = i.toString()
        i--
    }
}