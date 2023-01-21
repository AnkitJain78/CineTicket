package com.example.moviebooking

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviebooking.adapter.SeatAdapter
import com.example.moviebooking.databinding.ActivitySeatingBinding
import com.example.moviebooking.uiFragments.BottomSheetTicket
import com.example.moviebooking.viewModel.ItemViewModel

class SeatingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySeatingBinding
    private lateinit var adapterRoyale: SeatAdapter
    private lateinit var adapterClub: SeatAdapter
    private lateinit var adapterExecutive: SeatAdapter
    private lateinit var adapterPlatinum: SeatAdapter
    private val itemViewModel: ItemViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatingBinding.inflate(layoutInflater)
        adapterRoyale = SeatAdapter(40, this, binding.recyclerViewRoyale, 160)
        adapterClub = SeatAdapter(40, this, binding.recyclerViewClub, 80)
        adapterExecutive = SeatAdapter(40, this, binding.recyclerViewExecutive, 40)
        adapterPlatinum = SeatAdapter(40, this, binding.recyclerViewPlatinum, 120)
        setContentView(binding.root)
        supportActionBar?.title = "Seats"
        binding.recyclerViewRoyale.layoutManager =
            GridLayoutManager(this, 8, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewClub.layoutManager =
            GridLayoutManager(this, 8, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewExecutive.layoutManager =
            GridLayoutManager(this, 8, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewPlatinum.layoutManager =
            GridLayoutManager(this, 8, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewRoyale.adapter = adapterRoyale
        binding.recyclerViewClub.adapter = adapterClub
        binding.recyclerViewExecutive.adapter = adapterExecutive
        binding.recyclerViewPlatinum.adapter = adapterPlatinum
        binding.movieTck.setOnClickListener {
            BottomSheetTicket().show(supportFragmentManager, "ticket Quantity")
        }
        itemViewModel.tckQnt.observe(this, Observer { item ->
            binding.movieTckQntTxt.text = item
        })
    }
}