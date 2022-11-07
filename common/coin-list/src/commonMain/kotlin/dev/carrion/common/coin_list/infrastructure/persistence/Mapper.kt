package dev.carrion.common.coin_list.infrastructure.persistence

import dev.carrion.common.coin_list.domain.*
import dev.carrion.sqldelight.database.CoinList

fun CoinList.toDomain(): CoinListItem =
    CoinListItem(
        Id(this.id),
        Symbol(this.symbol),
        Name(this.name),
        Icon(this.icon),
        Price(this.price),
        PriceChange(this.priceChange),
        PriceChangePercent(this.priceChangePercent),
        OwnedAmount(0.0)
    )

fun CoinListItem.toDatabase(): CoinList =
    CoinList(
        id = this.id.value,
        symbol = this.symbol.value,
        name = this.name.value,
        icon = this.icon.value,
        price = this.price.value,
        priceChange = this.priceChange.value,
        priceChangePercent = this.priceChangePercent.value
    )