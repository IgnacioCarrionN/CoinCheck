package dev.carrion.common.coin_list.domain

import dev.carrion.common.coin_list.domain.extensions.toUIString

interface PricePrintable {
    val value: Double
    fun toUIString(decimals: Int = 4): String {
        return value.toUIString(decimals)
    }
}