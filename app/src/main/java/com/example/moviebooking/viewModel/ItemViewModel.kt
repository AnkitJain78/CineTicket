package com.example.moviebooking.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel : ViewModel() {
    private val mutableName = MutableLiveData<String>()
    val name: LiveData<String> get() = mutableName
    private val mutablePhone = MutableLiveData<String>()
    val phone: LiveData<String> get() = mutablePhone
    private val mutableTicketsQnt = MutableLiveData<String>()
    val tckQnt: LiveData<String> get() = mutableTicketsQnt

    fun selectName(item: String) {
        mutableName.value = item
    }

    fun selectPhone(item: String) {
        mutablePhone.value = item
    }

    fun selectTckQtn(item: String) {
        mutableTicketsQnt.value = item
    }
}