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
    private var mainHandler: Handler? = null
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scope = lifecycleScope
        scope.launch {

        }
        val repository = TickerRepository(RetrofitBuilder.api)
        val myViewModel: MyViewModel =
            ViewModelProvider(this, ViewModelFactory(repository)).get(MyViewModel::class.java)
         mainHandler = Handler(Looper.getMainLooper())

        runnable = object : Runnable {
            override fun run() {
                myViewModel.myIntent.tryEmit(MyIntent.GetPairs)
                mainHandler!!.postDelayed(this, 5000)
            }
        }
        mainHandler!!.post(runnable)


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
        mainHandler?.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        mainHandler?.post(runnable)

    }

}