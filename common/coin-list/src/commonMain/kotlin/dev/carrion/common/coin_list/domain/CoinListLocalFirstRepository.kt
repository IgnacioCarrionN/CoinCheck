package dev.carrion.common.coin_list.domain

class CoinListLocalFirstRepository(
    private val localDataSource: CoinListDataSource.Local,
    private val remoteDataSource: CoinListDataSource.Remote
) : CoinListRepository {

    override suspend fun getCoinList(): List<CoinListItem> {
        return localDataSource.getCoinList()
    }

    override suspend fun updateCoinList(): Result<Unit> = kotlin.runCatching {
        val newCoins = remoteDataSource.getCoinList()
        localDataSource.updateCoinList(newCoins)
    }

}