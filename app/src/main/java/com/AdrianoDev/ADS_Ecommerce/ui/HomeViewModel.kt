package com.AdrianoDev.ADS_Ecommerce.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = null
    }
    val text: LiveData<String> = _text
}