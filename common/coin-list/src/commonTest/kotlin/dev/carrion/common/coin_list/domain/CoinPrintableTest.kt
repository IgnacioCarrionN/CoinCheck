package dev.carrion.common.coin_list.domain

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CoinPrintableTest {

    private lateinit var coinPrintable: PricePrintable

    @BeforeTest
    fun SetUp() {
        coinPrintable = object : PricePrintable {
            override val value: Double = 0.01195
        }
    }

    @Test
    fun `Should round to 3 significant digits`() {
        val actual = coinPrintable.toUIString(3)
        assertEquals("0.012", actual)
    }

    @Test
    fun `Should round to 1 significant digits`() {
        val actual = coinPrintable.toUIString(1)
        assertEquals("0.01", actual)
    }

    @Test
    fun `Should round to bigger significant digits`() {
        val actual = coinPrintable.toUIString(10)
        assertEquals("0.01195", actual)
    }

    @Test
    fun `Throw error for 0 or negative decimals`() {
        assertFailsWith<DomainException>("Decimals should be bigger than 0") {
            coinPrintable.toUIString(0)
        }
    }

}