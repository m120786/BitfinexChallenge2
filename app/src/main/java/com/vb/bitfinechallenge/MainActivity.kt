package com.vb.bitfinechallenge

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.vb.bitfinechallenge.api.RetrofitBuilder
import com.vb.bitfinechallenge.intent.MyIntent
import com.vb.bitfinechallenge.model.domain.Ticker
import com.vb.bitfinechallenge.repository.TickerRepository
import com.vb.bitfinechallenge.ui.theme.BitfineChallengeTheme
import com.vb.bitfinechallenge.view.CoinListView
import com.vb.bitfinechallenge.viewModel.MyViewModel
import com.vb.bitfinechallenge.viewModel.ViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val mainHandler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = TickerRepository(RetrofitBuilder.api)
        val myViewModel: MyViewModel = ViewModelProvider(this, ViewModelFactory(repository)).get(MyViewModel::class.java)

        mainHandler.post(object: Runnable {
            override fun run() {

                var status = myViewModel.myIntent.tryEmit(MyIntent.GetPair)
                mainHandler.postDelayed(this, 5000)
            }

        })

        setContent {
            BitfineChallengeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CoinListView(myViewModel = myViewModel)
                }
            }
        }
    }

}