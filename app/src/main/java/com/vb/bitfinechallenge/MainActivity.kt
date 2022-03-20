package com.vb.bitfinechallenge

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.vb.bitfinechallenge.api.RetrofitBuilder
import com.vb.bitfinechallenge.intent.MyIntent
import com.vb.bitfinechallenge.repository.TickerRepository
import com.vb.bitfinechallenge.ui.theme.BitfineChallengeTheme
import com.vb.bitfinechallenge.view.CoinListView
import com.vb.bitfinechallenge.viewModel.MyViewModel
import com.vb.bitfinechallenge.viewModel.ViewModelFactory

class MainActivity : ComponentActivity() {
    private var mainHandler: Handler? = null
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = TickerRepository(RetrofitBuilder.api)
        val myViewModel: MyViewModel =
            ViewModelProvider(this, ViewModelFactory(repository)).get(MyViewModel::class.java)
        val mainHandler = Handler(Looper.getMainLooper())

        val runnable = object : Runnable {
            override fun run() {
                myViewModel.myIntent.tryEmit(MyIntent.GetPair)
                mainHandler.postDelayed(this, 5000)
                Toast.makeText(applicationContext, "Runnable called", Toast.LENGTH_SHORT).show()
            }
        }

        mainHandler.post(runnable)

//        myViewModel.startGettingData(mainHandler, runnable)

//        mainHandler.post(object : Runnable {
//            override fun run() {
//                myViewModel.myIntent.tryEmit(MyIntent.GetPair)
//                mainHandler.postDelayed(this, 5000)
//            }
//        })

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
        Toast.makeText(applicationContext, "OnPause", Toast.LENGTH_SHORT).show()

    }

    override fun onResume() {
        super.onResume()
        mainHandler?.post(runnable)

    }

    override fun onStop() {
        super.onStop()
        mainHandler?.removeCallbacks(runnable)
        Toast.makeText(applicationContext, "OnStop", Toast.LENGTH_SHORT).show()
    }

}