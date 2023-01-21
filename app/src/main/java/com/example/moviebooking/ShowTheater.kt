package com.example.moviebooking

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviebooking.adapter.CinemaAdapter
import com.example.moviebooking.adapter.DateAdapter
import com.example.moviebooking.databinding.ActivityShowTheaterBinding
import com.example.moviebooking.model.Cinema
import java.util.*

class ShowTheater : AppCompatActivity() {
    private lateinit var binding: ActivityShowTheaterBinding
    private lateinit var cinemaAdapter: CinemaAdapter
    private lateinit var dateAdapter: DateAdapter
    private var cinemaList = ArrayList<Cinema>()
    private var dateList = ArrayList<Date>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowTheaterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Cinemas"
        binding.cinemaRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.dateRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        cinemaList = arrayListOf(
            Cinema("C. Raj Mandir Cinema.", "Jaipur"),
            Cinema("Cinepolis Cinemas", "Jaipur"),
            Cinema("Carnival Cinemas", "Jaipur")
        )
        Log.d("cList", cinemaList.toString())
        cinemaAdapter = CinemaAdapter(cinemaList, this)
        binding.cinemaRecyclerView.adapter = cinemaAdapter
        dateList = arrayListOf(
            Date(2022, 1, 20),
            Date(2022, 1, 21),
            Date(2022, 1, 22),
            Date(2022, 1, 23),
            Date(2022, 1, 24),
            Date(2022, 1, 25)
        )
        dateAdapter = DateAdapter(dateList)
        binding.dateRecyclerView.adapter = dateAdapter
    }
}