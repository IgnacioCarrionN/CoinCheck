package dev.carrion.common.coin_list.domain

import dev.carrion.architecture.usecase.IOUseCase

class UpdateCoinListUseCase(
    private val repository: CoinListRepository
) : IOUseCase<Result<Unit>>(repository::updateCoinList)