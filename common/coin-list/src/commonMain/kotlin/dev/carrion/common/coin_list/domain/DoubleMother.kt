package dev.carrion.common.coin_list.domain

import kotlin.random.Random

object DoubleMother {

    fun create(): Double {
        return random()
    }

    private fun random(): Double {
        return Random.nextDouble()
    }

}