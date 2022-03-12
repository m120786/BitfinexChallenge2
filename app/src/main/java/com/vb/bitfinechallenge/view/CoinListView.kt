package com.vb.bitfinechallenge.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.vb.bitfinechallenge.R
import com.vb.bitfinechallenge.intent.MyIntent
import com.vb.bitfinechallenge.mainState.CoinState
import com.vb.bitfinechallenge.model.domain.CoinPair
import com.vb.bitfinechallenge.model.domain.Ticker
import com.vb.bitfinechallenge.viewModel.MyViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun CoinListView(myViewModel: MyViewModel) {
//    var ticker by remember { mutableStateOf(Ticker(0.0,0.0,0.0,0.0,0.0,0.0,0f,0.0)) }
    var pair by remember { mutableStateOf(CoinPair( "btcusd", R.drawable.ic_dogecoin_doge_logo,Ticker(0.0,0.0,0.0,0.0,0.0,0.0,0f,0.0))) }
    val listOfCoins = remember { mutableStateOf(emptyList<Ticker>())}
    var listOfPairs = mutableListOf<CoinPair>()

    var list = mutableListOf<Ticker>()

    LaunchedEffect(Unit) {
        myViewModel.myIntent.emit(MyIntent.GetPair)
        myViewModel.state.collect {
            when (it) {
                is CoinState.Pair -> {
                    pair = CoinPair(it.name, it.logo,it.ticker)
                    listOfPairs.add(pair)
                }
        }
        }
    }
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(listOfPairs) {
            Text("ticker"+ it.ticker)
        }
    }

}

