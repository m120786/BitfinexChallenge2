package com.vb.bitfinechallenge.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vb.bitfinechallenge.repository.TickerService
import java.lang.IllegalArgumentException

class ViewModelFactory(private val tickerService: TickerService): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            return MyViewModel(tickerService) as T
        }
        throw IllegalArgumentException("Unknown class name")
        }
    }
