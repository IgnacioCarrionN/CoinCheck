package dev.carrion.common.coin_list.domain

import kotlin.jvm.JvmInline

@JvmInline
value class OwnedAmount(override val value: Double) : PricePrintable {
    init {
        require(value >= 0) { "Owned amount can't be negative" }
    }
}