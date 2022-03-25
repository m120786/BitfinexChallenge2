package com.vb.bitfinechallenge.viewModel

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.bitfinechallenge.intent.MyIntent
import com.vb.bitfinechallenge.mainState.CoinState
import com.vb.bitfinechallenge.mainState.NetworkState
import com.vb.bitfinechallenge.model.domain.CoinPair
import com.vb.bitfinechallenge.network.NetworkStatus
import com.vb.bitfinechallenge.network.NetworkStatusListener
import com.vb.bitfinechallenge.repository.TickerService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyViewModel(private val tickerService: TickerService, context: Context): ViewModel() {
    val myIntent = MutableSharedFlow<MyIntent>(1,10)
    val coinState = MutableStateFlow<CoinState>(CoinState.Idle)
    val networkState = MutableStateFlow<NetworkState>(NetworkState.NotAvailable)
    val networkStatus = NetworkStatus(context)
    val mainHandler = Handler(Looper.getMainLooper())

    private lateinit var runnable: Runnable
    var coinPairList = ArrayList<CoinPair>()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            myIntent.collect {
                when (it) {
                    is MyIntent.GetPairs -> { getCoins() }
                    is MyIntent.StartGettingData -> {startGettingData()}
                }
            }
            networkState.collect {
                when(it) {
                    is NetworkState.Available -> { startGettingData() }
                    is NetworkState.NotAvailable -> { stopGettingData() }
                }
            }
        }
    }

     fun startGettingData() {
        networkStatus.registerForNetworkStatus()

        val runnable = object : Runnable {
            override fun run() {
                myIntent.tryEmit(MyIntent.GetPairs)
                mainHandler.postDelayed(this, 5000)
            }
        }
        mainHandler.post(runnable)
    }

     fun stopGettingData() {
        mainHandler.removeCallbacks(runnable)
        networkStatus.unregisterForNetworkStatus()
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