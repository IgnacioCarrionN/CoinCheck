package dev.carrion.common.coin_list.domain

interface CoinListRepository {
    suspend fun getCoinList(): List<CoinListItem>
    suspend fun updateCoinList(): Result<Unit>
}