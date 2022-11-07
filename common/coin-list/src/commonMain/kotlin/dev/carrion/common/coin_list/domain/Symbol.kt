package dev.carrion.common.coin_list.domain

import kotlin.jvm.JvmInline

@JvmInline
value class Symbol(val value: String) {
    init {
        require(value.isNotEmpty()) { "Symbol can't be empty" }
    }
}