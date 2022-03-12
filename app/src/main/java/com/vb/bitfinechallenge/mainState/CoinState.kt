package com.vb.bitfinechallenge.mainState

import com.vb.bitfinechallenge.model.domain.Ticker

sealed class CoinState {
    object Idle: CoinState()
    object Loading: CoinState()
    object DoneLoading: CoinState()

    data class Pair(val name: String, val logo: Int, val ticker: Ticker): CoinState()
    data class Error(val error: String): CoinState()

}
