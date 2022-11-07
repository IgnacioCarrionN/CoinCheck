package dev.carrion.common.coin_list.domain

object CoinListItemMother {

    fun create(): CoinListItem {
        return random()
    }

    private fun random(): CoinListItem {
        return CoinListItem(
            Id(StringMother.create()),
            Symbol(StringMother.create()),
            Name(StringMother.create()),
            Icon(StringMother.create()),
            Price(DoubleMother.create()),
            PriceChange(DoubleMother.create()),
            PriceChangePercent(DoubleMother.create()),
            OwnedAmount(DoubleMother.create())
        )
    }
}