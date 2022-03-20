package com.vb.bitfinechallenge.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vb.bitfinechallenge.mainState.CoinState
import com.vb.bitfinechallenge.model.domain.CoinPair
import com.vb.bitfinechallenge.viewModel.MyViewModel

@Composable
fun CoinListView(myViewModel: MyViewModel) {

    var listOfPairs = remember { mutableStateOf(ArrayList<CoinPair>()) }

    val state = myViewModel.coinState.collectAsState()
    when (state.value) {
        is CoinState.ListOfPairs -> {
            listOfPairs.value = (state.value as CoinState.ListOfPairs).list
        }
    }

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(listOfPairs.value, key = { it.id }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                shape = RoundedCornerShape(3.dp)
            ) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = it.logo),
                        contentDescription = "logo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                    Column(modifier = Modifier.padding(2.dp)) {
                        Text("${it.id.take(3).uppercase()}")
                        Text("${it.name}")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text("$ " + "${it.ticker.last_price}")
                    }
                }
            }

        }
    }

}



