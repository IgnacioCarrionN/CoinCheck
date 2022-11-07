package dev.carrion.common.coin_list.domain

import kotlin.jvm.JvmInline

@JvmInline
value class Price(override val value: Double) : PricePrintable {
    init {
        require(value >= 0) { "Price should be positive" }
    }
}