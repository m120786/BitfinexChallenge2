package com.vb.bitfinechallenge.repository

import com.vb.bitfinechallenge.model.domain.Ticker
import com.vb.bitfinechallenge.model.network.TickerDto

interface TickerService {
    suspend fun getCoinById(coiId: String): Ticker
}