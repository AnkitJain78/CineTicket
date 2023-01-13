package com.example.moviebooking.uiFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.moviebooking.R
import com.example.moviebooking.databinding.FragmentBottomSheetBinding
import com.example.moviebooking.databinding.FragmentBottomSheetPhoneBinding
import com.example.moviebooking.viewModel.ItemViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetPhoneFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetPhoneBinding
    private  val itemViewModel: ItemViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetPhoneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPhoneOk.setOnClickListener {
            onItemClicked(binding.editTextUpdatePhone.editText?.text.toString())
            dismiss()
        }
        binding.btnPhoneCancel.setOnClickListener {
            dismiss()
        }
    }
    fun onItemClicked(item: String) {
        itemViewModel.selectPhone(item)
    }


}