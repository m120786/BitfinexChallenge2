package com.vb.bitfinechallenge.viewModel

import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vb.bitfinechallenge.R
import com.vb.bitfinechallenge.intent.MyIntent
import com.vb.bitfinechallenge.mainState.CoinState
import com.vb.bitfinechallenge.model.domain.CoinPair
import com.vb.bitfinechallenge.model.domain.Ticker
import com.vb.bitfinechallenge.repository.TickerRepository
import com.vb.bitfinechallenge.repository.TickerService
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyViewModel(private val tickerService: TickerService): ViewModel() {
    val myIntent = MutableSharedFlow<MyIntent>()
    val state = MutableStateFlow<CoinState>(CoinState.Idle)
    val coinPairList = ArrayList<CoinPair>()

    val iconDoge = R.drawable.ic_dogecoin_doge_logo
    val iconBTC = R.drawable.ic_wrapped_bitcoin_wbtc_logo
    val iconLTC = R.drawable.ic_litecoin_ltc_logo
    val iconETH = R.drawable.ic_ethereum_eth_logo
    val iconXRP = R.drawable.ic_xrp_xrp_logo

    var coinMap: HashMap<String, Int> = hashMapOf("btcusd" to iconBTC, "ltcusd" to iconLTC, "dogusd" to iconDoge, "xrpusd" to iconXRP, "ethusd" to iconETH)
    var id = "btcusd"
    val coinList = listOf("btcusd", "ltcusd", "dogusd", "xrpusd", "ethusd")

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            myIntent.collect {
                when (it) {
                    is MyIntent.GetPair -> { getCoins()
                    }
                }
            }
        }
    }

    private fun getCoins() {
        viewModelScope.launch {
            state.value = CoinState.Loading
            state.value = try {
                tickerService.getCoins()
                CoinState.ListOfPairs(coinPairList)
            } catch (e: Exception) {
                CoinState.Error(e.toString())
            }
        }
    }
}