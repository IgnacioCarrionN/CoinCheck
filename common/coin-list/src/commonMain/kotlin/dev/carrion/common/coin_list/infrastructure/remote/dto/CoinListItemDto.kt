package dev.carrion.common.coin_list.infrastructure.remote.dto

import dev.carrion.common.coin_list.domain.CoinListItem
import dev.carrion.common.coin_list.domain.*
import kotlinx.serialization.Serializable

@Serializable
data class CoinListItemDto(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    val current_price: Double,
    val price_change_24h: Double,
    val price_change_percentage_24h: Double,
)

fun CoinListItemDto.toDomain() =
    CoinListItem(
        id = Id(this.id),
        symbol = Symbol(this.symbol),
        name = Name(this.name),
        icon = Icon(this.image),
        price = Price(this.current_price),
        priceChange = PriceChange(this.price_change_24h),
        priceChangePercent = PriceChangePercent(this.price_change_percentage_24h),
        ownedAmount = OwnedAmount(0.0)
    )