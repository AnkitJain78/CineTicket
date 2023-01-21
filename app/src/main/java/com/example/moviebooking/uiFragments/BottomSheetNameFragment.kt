package com.example.moviebooking.uiFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.moviebooking.databinding.FragmentBottomSheetBinding
import com.example.moviebooking.viewModel.ItemViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private val itemViewModel: ItemViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnOk.setOnClickListener {
            onItemClicked(binding.editTextUpdateName.editText?.text.toString())
            dismiss()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    fun onItemClicked(item: String) {
        itemViewModel.selectName(item)
    }

}