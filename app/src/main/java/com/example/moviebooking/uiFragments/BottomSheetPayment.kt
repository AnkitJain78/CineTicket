package com.example.moviebooking.uiFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviebooking.databinding.FragmentBottomSheetPaymentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetPayment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetPaymentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonPayment.setOnClickListener {

        }


    }
}