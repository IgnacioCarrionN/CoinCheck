package dev.carrion.common.coin_list.domain

data class CoinListItem(
    val id: Id,
    val symbol: Symbol,
    val name: Name,
    val icon: Icon,
    val price: Price,
    val priceChange: PriceChange,
    val priceChangePercent: PriceChangePercent,
    val ownedAmount: OwnedAmount
)