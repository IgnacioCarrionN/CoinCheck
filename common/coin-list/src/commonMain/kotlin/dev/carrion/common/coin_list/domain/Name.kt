package dev.carrion.common.coin_list.domain

import kotlin.jvm.JvmInline

@JvmInline
value class Name(val value: String) {
    init {
        require(value.isNotEmpty()) { "Name it's empty" }
    }
}