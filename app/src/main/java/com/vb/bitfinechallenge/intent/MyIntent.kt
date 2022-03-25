package com.vb.bitfinechallenge.intent

sealed class MyIntent {
    object GetPairs: MyIntent()
    object StartGettingData: MyIntent()
    object StopGettingData: MyIntent()
    object GetNetworkStatus: MyIntent()
    object StopNetworkStatus: MyIntent()

}