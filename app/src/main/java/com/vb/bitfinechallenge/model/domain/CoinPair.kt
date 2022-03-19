package com.vb.bitfinechallenge.model.domain

data class CoinPair(
    val id: String,
    val name: String,
    val logo: Int,
    val ticker: Ticker
)
