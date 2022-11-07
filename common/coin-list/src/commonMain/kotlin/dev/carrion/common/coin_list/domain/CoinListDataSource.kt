package dev.carrion.common.coin_list.domain

import kotlinx.coroutines.flow.Flow

sealed interface CoinListDataSource {
    interface Local : CoinListDataSource {
        suspend fun updateCoinList(coinList: List<CoinListItem>)
        suspend fun getCoinList():List<CoinListItem>
    }
    interface Remote : CoinListDataSource {
        suspend fun getCoinList(): List<CoinListItem>
    }
}