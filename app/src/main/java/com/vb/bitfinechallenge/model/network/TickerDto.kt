package com.vb.bitfinechallenge.model.network

import com.vb.bitfinechallenge.model.domain.Ticker

data class TickerDto(
    val ask: String,
    val bid: String,
    val high: String,
    val last_price: String,
    val low: String,
    val mid: String,
    val timestamp: String,
    val volume: String
)

fun TickerDto.toTicker(): Ticker {
    return Ticker(
        ask = ask.toDouble(),
        bid = bid.toDouble(),
        high = high.toDouble(),
        last_price = last_price.toDouble(),
        low = low.toDouble(),
        mid = mid.toDouble(),
        timestamp = timestamp.toFloat(),
        volume = volume.toDouble()
    )
}