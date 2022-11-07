package dev.carrion.common.coin_list.domain

import dev.carrion.architecture.usecase.IOUseCase

class GetCoinListUseCase(
    private val repository: CoinListRepository
) : IOUseCase<List<CoinListItem>>(repository::getCoinList)