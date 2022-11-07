package dev.carrion.common.coin_list.domain

import kotlin.jvm.JvmInline

@JvmInline
value class Icon(val value: String) {
    init {
        require(value.isNotEmpty()) { "Icon should not be empty" }
    }
}
