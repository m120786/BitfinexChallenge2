package com.vb.bitfinechallenge.viewModel

import android.content.Context
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

class MyViewModel(private val tickerService: TickerService, private val context: Context): ViewModel(), NetworkStatusListener {
    val myIntent = MutableSharedFlow<MyIntent>(1,10)
    val coinState = MutableStateFlow<CoinState>(CoinState.Idle)
    val networkState = MutableStateFlow<NetworkState>(NetworkState.NotAvailable)
    val networkStatus = NetworkStatus(context)

    var coinPairList = ArrayList<CoinPair>()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            myIntent.collect {
                when (it) {
                    is MyIntent.GetPairs -> { getCoins() }
                    is MyIntent.GetNetworkStatus -> {registerForNetworkState()}
                    is MyIntent.StopNetworkStatus -> {unregisterFromNetworkState()}
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

    override fun registerForNetworkState() {
        networkStatus.registerForNetworkStatus()


    }

    override fun unregisterFromNetworkState() {
        networkStatus.unregisterForNetworkStatus()
    }


}