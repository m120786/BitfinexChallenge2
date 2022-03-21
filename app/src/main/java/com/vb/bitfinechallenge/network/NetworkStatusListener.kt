package com.vb.bitfinechallenge.network

interface NetworkStatusListener {
    fun registerForNetworkState()
    fun unregisterFromNetworkState()
}