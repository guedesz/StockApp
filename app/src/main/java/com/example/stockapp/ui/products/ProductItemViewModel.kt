package com.example.stockapp.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductItemViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is item product fragment"
    }
    val text: LiveData<String> = _text

}