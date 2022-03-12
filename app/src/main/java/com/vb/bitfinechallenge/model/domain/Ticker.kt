package com.vb.bitfinechallenge.model.domain

data class Ticker(
    val ask: Double,
    val bid: Double,
    val high: Double,
    val last_price: Double,
    val low: Double,
    val mid: Double,
    val timestamp: Float,
    val volume: Double
)