package dev.carrion.coincheck

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dev.carrion.architecture.resource.Resource
import dev.carrion.coincheck.common.compose.image.RemoteImage
import dev.carrion.common.coin_list.domain.CoinListItem
import dev.carrion.common.coin_list.domain.CoinListItemMother
import dev.carrion.common.coin_list.domain.store.MainAction

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun MainContent() {
        val state by viewModel.state.collectAsState()
        val pullState = rememberPullRefreshState(
            refreshing = state.remote is Resource.Loading,
            onRefresh = { viewModel.send(MainAction.UpdateCoinList) }
        )
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Box(
                modifier = Modifier
                    .pullRefresh(pullState)
                    .padding(paddingValues),
                contentAlignment = Alignment.TopCenter
            ) {
                when (val coinList = state.coinList) {
                    Resource.Idle -> {}
                    Resource.Loading -> CircularProgressIndicator()
                    is Resource.Loaded -> if (coinList.data.isNotEmpty()) {
                        CoinListView(coinList = coinList.data)
                    } else {
                        Text("List empty")
                    }
                    is Resource.Error -> Text("Database error")
                }
                PullRefreshIndicator(
                    state.remote is Resource.Loading,
                    state = pullState
                )
                when (val remoteState = state.remote) {
                    is Resource.Error -> {
                        Text(remoteState.exception.message ?: "Internet error")
                    }
                    else -> {}
                }
            }
        }

    }

    @Composable
    private fun CoinListView(
        coinList: List<CoinListItem>,
    ) {
        val stateList = rememberLazyListState()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = stateList
        ) {
            items(coinList) { item ->
                CoinListItemView(coinListItem = item)
            }
        }
    }

    @Composable
    private fun CoinListItemView(coinListItem: CoinListItem) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RemoteImage(url = coinListItem.icon.value, modifier = Modifier.size(24.dp))
            Text(coinListItem.name.value)
            Text(coinListItem.price.toUIString())
            Text(coinListItem.priceChangePercent.toUIString())
        }
    }

    @Preview
    @Composable
    private fun CoinListItemView_Preview() {
        CoinListItemView(coinListItem = CoinListItemMother.create())
    }

}