package com.vb.bitfinechallenge.repository

import com.vb.bitfinechallenge.R
import com.vb.bitfinechallenge.api.ApiService
import com.vb.bitfinechallenge.model.domain.CoinBasicData
import com.vb.bitfinechallenge.model.domain.CoinPair
import com.vb.bitfinechallenge.model.network.toTicker

class TickerRepository(val api: ApiService) : TickerService {

    val iconDoge = R.drawable.ic_dogecoin_doge_logo
    val iconBTC = R.drawable.ic_wrapped_bitcoin_wbtc_logo
    val iconLTC = R.drawable.ic_litecoin_ltc_logo
    val iconETH = R.drawable.ic_ethereum_eth_logo
    val iconXRP = R.drawable.ic_xrp_xrp_logo

    val coinList: ArrayList<CoinBasicData> = arrayListOf(
        CoinBasicData("btcusd", "Bitcoin", iconBTC),
        CoinBasicData("ltcusd", "Litecoin", iconLTC),
        CoinBasicData("dogusd", "Dogecoin", iconDoge),
        CoinBasicData("xrpusd", "Ripple", iconXRP),
        CoinBasicData("ethusd", "Ethereum", iconETH)
    )
    var listOfTicker = ArrayList<CoinPair>()


    override suspend fun getCoins(): ArrayList<CoinPair> {
        coinList.forEach { coinBasicData ->
            var tickerData = api.getPairById(coinBasicData.id).toTicker()
            if (checkIfAlreadyAdded(coinBasicData.id, listOfTicker)) {
                listOfTicker.set(listOfTicker.indexOfFirst { coinPair -> coinPair.id == coinBasicData.id}, CoinPair(coinBasicData.id, coinBasicData.name, coinBasicData.logo, tickerData))
            } else {
                listOfTicker.add(CoinPair(coinBasicData.id, coinBasicData.name, coinBasicData.logo, tickerData))
            }
        }
        return listOfTicker
    }

    private fun checkIfAlreadyAdded(id: String, listOfTicker: java.util.ArrayList<CoinPair>): Boolean {
        return listOfTicker.any { it.id == id }
    }


}