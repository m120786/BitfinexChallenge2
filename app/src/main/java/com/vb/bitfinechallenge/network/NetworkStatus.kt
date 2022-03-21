package com.vb.bitfinechallenge.network

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat.getSystemService
import com.vb.bitfinechallenge.mainState.NetworkState
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkStatus(context: Context) {

    val networkState = MutableStateFlow<NetworkState>(NetworkState.NotAvailable)

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    fun registerForNetworkStatus() {
        connectivityManager.requestNetwork(networkRequest, object : ConnectivityManager.NetworkCallback() {
           override fun onAvailable(network: Network) {
                super.onAvailable(network)
                networkState.value = NetworkState.Available
            }

            // lost network connection
            override fun onLost(network: Network) {
                super.onLost(network)
                networkState.value = NetworkState.NotAvailable
            }
        })

    }
    fun unregisterForNetworkStatus() {
        connectivityManager.unregisterNetworkCallback(ConnectivityManager.NetworkCallback())
    }
}