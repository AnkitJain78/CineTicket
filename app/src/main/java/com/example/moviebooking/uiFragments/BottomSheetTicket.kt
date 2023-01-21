package com.example.moviebooking.uiFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.moviebooking.databinding.FragmentBottomSheetTicketBinding
import com.example.moviebooking.viewModel.ItemViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetTicket : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetTicketBinding
    private val itemViewModel: ItemViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.t1.setOnClickListener {
            onItemClicked(binding.t1.text.toString())
            dismiss()
        }
        binding.t2.setOnClickListener {
            onItemClicked(binding.t2.text.toString())
            dismiss()
        }
        binding.t3.setOnClickListener {
            onItemClicked(binding.t3.text.toString())
            dismiss()
        }
        binding.t4.setOnClickListener {
            onItemClicked(binding.t4.text.toString())
            dismiss()
        }
        binding.t5.setOnClickListener {
            onItemClicked(binding.t5.text.toString())
            dismiss()
        }
        binding.t6.setOnClickListener {
            onItemClicked(binding.t6.text.toString())
            dismiss()
        }
        binding.t7.setOnClickListener {
            onItemClicked(binding.t7.text.toString())
            dismiss()
        }
        binding.t8.setOnClickListener {
            onItemClicked(binding.t8.text.toString())
            dismiss()
        }
    }

    fun onItemClicked(item: String) {
        itemViewModel.selectTckQtn(item + " Tickets")
    }
}