package dev.carrion.common.coin_list.domain

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class DomainException(message: String) : CoinListException(message)

class RemoteException(message: String) : CoinListException(message)

sealed class CoinListException(message: String) : Exception(message)

@OptIn(ExperimentalContracts::class)
inline fun require(value: Boolean, lazyMessage: () -> Any): Unit {
    contract {
        returns() implies value
    }
    if (!value) {
        val message = lazyMessage()
        throw DomainException(message.toString())
    }
}
