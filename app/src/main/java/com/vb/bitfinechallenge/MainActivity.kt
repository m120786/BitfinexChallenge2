package com.vb.bitfinechallenge

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.vb.bitfinechallenge.api.RetrofitBuilder
import com.vb.bitfinechallenge.intent.MyIntent
import com.vb.bitfinechallenge.repository.TickerRepository
import com.vb.bitfinechallenge.ui.theme.BitfineChallengeTheme
import com.vb.bitfinechallenge.view.CoinListView
import com.vb.bitfinechallenge.viewModel.MyViewModel
import com.vb.bitfinechallenge.viewModel.ViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = baseContext
        val repository = TickerRepository(RetrofitBuilder.api)
        myViewModel = ViewModelProvider(this, ViewModelFactory(repository, context)).get(MyViewModel::class.java)

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

    override fun onPause() {
        super.onPause()
        myViewModel.stopGettingData()
    }

    override fun onResume() {
        super.onResume()
        myViewModel.startGettingData()
    }

}