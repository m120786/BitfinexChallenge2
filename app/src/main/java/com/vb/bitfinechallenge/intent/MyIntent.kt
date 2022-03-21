package com.vb.bitfinechallenge.intent

sealed class MyIntent {
    object GetPairs: MyIntent()
    object GetNetworkStatus: MyIntent()
    object StopNetworkStatus: MyIntent()

}