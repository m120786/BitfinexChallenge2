package com.vb.bitfinechallenge.repository

import com.vb.bitfinechallenge.R
import com.vb.bitfinechallenge.api.ApiService
import com.vb.bitfinechallenge.model.domain.CoinPair
import com.vb.bitfinechallenge.model.domain.Ticker
import com.vb.bitfinechallenge.model.network.toTicker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class TickerRepository(val api: ApiService): TickerService {

    val iconDoge = R.drawable.ic_dogecoin_doge_logo
    val iconBTC = R.drawable.ic_wrapped_bitcoin_wbtc_logo
    val iconLTC = R.drawable.ic_litecoin_ltc_logo
    val iconETH = R.drawable.ic_ethereum_eth_logo
    val iconXRP = R.drawable.ic_xrp_xrp_logo
    var coinMap: HashMap<String, Int> = hashMapOf("btcusd" to iconBTC, "ltcusd" to iconLTC, "dogusd" to iconDoge, "xrpusd" to iconXRP, "ethusd" to iconETH)
    var listOfCoins = listOf("btcusd", "ltcusd")
    var listOfTicker = ArrayList<CoinPair>()


    override suspend fun getCoins(): ArrayList<CoinPair> {
        coinMap.forEach {
            var tickerData = api.getPairById(it.component1()).first().toTicker()
            listOfTicker.add(CoinPair(it.component1(), it.component2(), tickerData))
        }
        return listOfTicker
    }
}