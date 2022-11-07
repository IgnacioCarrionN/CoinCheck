package dev.carrion.common.coin_list.domain.extensions

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.DecimalMode
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import dev.carrion.common.coin_list.domain.DomainException
import kotlin.math.pow
import kotlin.math.roundToInt


internal fun Double.toUIString(decimals: Int = 6): String {
    return try {
        this.round(decimals)
    } catch (e: IllegalArgumentException) {
        throw DomainException("Decimals should be bigger than 0")
    }
}

private fun Double.round(digits: Int): String {
    require(digits > 0)
    DecimalMode()
    return BigDecimal.fromDouble(
        this,
        DecimalMode(digits.toLong(), RoundingMode.ROUND_HALF_AWAY_FROM_ZERO)
    ).toPlainString()
}

private fun Double.roundRecursive(decimals: Int, originalDecimals: Int = decimals): Double {
    require(decimals > 0)
    if (this.getDecimalCount() == originalDecimals) {
        return this
    }
    if (this.getDecimalCount() < decimals) {
        return this + (0.0 / 10.0.pow(decimals))
    }
    val nextDecimalCount = this.getDecimalCount() - 1
    return this.roundTo(nextDecimalCount).roundRecursive(nextDecimalCount, originalDecimals)
}

private fun Double.getDecimalCount(): Int {
    return this.toString().substringAfter('.').length
}

//fun Double.roundTo(decimals: Int): Double {
//    var multiplier = 1.0
//    repeat(decimals) { multiplier *= 10 }
//    return (this * multiplier).roundToInt() / multiplier
//}

private fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}