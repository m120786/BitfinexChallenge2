package com.vb.bitfinechallenge.viewModel

import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.bitfinechallenge.intent.MyIntent
import com.vb.bitfinechallenge.mainState.CoinState
import com.vb.bitfinechallenge.mainState.NetworkState
import com.vb.bitfinechallenge.model.domain.CoinPair
import com.vb.bitfinechallenge.repository.TickerService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyViewModel(private val tickerService: TickerService): ViewModel() {
    val myIntent = MutableSharedFlow<MyIntent>(1,10)
    val coinState = MutableStateFlow<CoinState>(CoinState.Idle)

    var coinPairList = ArrayList<CoinPair>()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            myIntent.collect {
                when (it) {
                    is MyIntent.GetPair -> { getCoins() }
                }
            }
        }
    }

    private fun getCoins() {
        viewModelScope.launch {
            coinState.value = CoinState.Loading
            coinState.value = try {
                coinPairList = tickerService.getCoins()
                CoinState.ListOfPairs(coinPairList)
            } catch (e: Exception) {
                CoinState.Error(e.toString())
            }
        }
    }


}