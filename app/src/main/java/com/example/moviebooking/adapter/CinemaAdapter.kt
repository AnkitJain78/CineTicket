package com.example.moviebooking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.moviebooking.R
import com.example.moviebooking.model.Cinema

class CinemaAdapter(private val cinemaList: List<Cinema>, val context: Context) :
    Adapter<CinemaAdapter.CinemaViewHolder>() {

    class CinemaViewHolder(itemView: View) : ViewHolder(itemView) {
        var cinemaName: TextView = itemView.findViewById(R.id.cinema_name)
        var cinemaLocation: TextView = itemView.findViewById(R.id.txt_cinema_location)
        var recyclerView: RecyclerView = itemView.findViewById(R.id.timeRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_theater, parent, false)
        return CinemaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        val currentCinema = cinemaList[position]
        holder.cinemaName.text = currentCinema.cinema_name
        holder.cinemaLocation.text = currentCinema.cinema_location
        holder.recyclerView.layoutManager = GridLayoutManager(context, 3)
        holder.recyclerView.adapter = TimeAdapter(
            arrayListOf("09:10 AM", "12:15 PM", "03:20 PM", "06:25 PM", "09:30 PM"),
            context
        )
    }

    override fun getItemCount(): Int {
        return cinemaList.size
    }
}