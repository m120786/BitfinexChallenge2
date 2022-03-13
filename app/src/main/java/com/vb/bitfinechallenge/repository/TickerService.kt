package com.vb.bitfinechallenge.repository

import com.vb.bitfinechallenge.model.domain.CoinPair
import com.vb.bitfinechallenge.model.domain.Ticker
import com.vb.bitfinechallenge.model.network.TickerDto
import kotlinx.coroutines.flow.Flow

interface TickerService {
    suspend fun getCoins(): ArrayList<CoinPair>
}