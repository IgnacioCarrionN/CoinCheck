package dev.carrion.common.coin_list.infrastructure.persistence

import dev.carrion.common.coin_list.domain.CoinListDataSource
import dev.carrion.common.coin_list.domain.CoinListItem
import dev.carrion.sqldelight.database.CoinCheck

class CoinListLocalDataSource(
    private val coinCheck: CoinCheck
) : CoinListDataSource.Local {
    override suspend fun updateCoinList(coinList: List<CoinListItem>) {
        coinCheck.coinListQueries.transaction {
            coinList.map {
                it.toDatabase()
            }.forEach {
                coinCheck.coinListQueries.insertCoin(it)
            }
        }
    }

    override suspend fun getCoinList(): List<CoinListItem> {
        return coinCheck.coinListQueries.selectCoins()
            .executeAsList()
            .map { coinList ->
                coinList.toDomain()
            }
    }
}