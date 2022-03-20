package com.vb.bitfinechallenge.mainState

sealed class NetworkState {
    object NotAvailable: NetworkState()
    object Available: NetworkState()
    object Reconnecting: NetworkState()
}
