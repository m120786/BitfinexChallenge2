package com.vb.bitfinechallenge.repository

import com.vb.bitfinechallenge.api.ApiService
import com.vb.bitfinechallenge.model.domain.Ticker
import com.vb.bitfinechallenge.model.network.toTicker

class TickerRepository(val api: ApiService): TickerService {
    override suspend fun getCoinById(pairId: String): Ticker {
       val tickerDto =  api.getPairById(pairId)
        return tickerDto.toTicker()
    }
}