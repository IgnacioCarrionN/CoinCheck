package dev.carrion.common.coin_list.infrastructure.remote

import dev.carrion.common.coin_list.domain.CoinListDataSource
import dev.carrion.common.coin_list.domain.CoinListItem
import dev.carrion.common.coin_list.infrastructure.remote.dto.CoinListItemDto
import dev.carrion.common.coin_list.infrastructure.remote.dto.toDomain
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

const val BASE_URL = "https://api.coingecko.com/api/v3"

class CoinListRemoteDataSource(
    private val httpClient: HttpClient
) : CoinListDataSource.Remote {
    
    override suspend fun getCoinList(): List<CoinListItem> {
        val coinListDto = httpClient.get("$BASE_URL/coins/markets?vs_currency=usd").body<List<CoinListItemDto>>()
        return coinListDto.map { it.toDomain() }
    }

}