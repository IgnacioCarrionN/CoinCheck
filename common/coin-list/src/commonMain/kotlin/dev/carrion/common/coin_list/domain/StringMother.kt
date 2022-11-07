package dev.carrion.common.coin_list.domain

object StringMother {
    fun create(): String {
        return random()
    }

    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private const val STRING_LENGTH = 5
    private fun random(): String {
        return List(STRING_LENGTH) { charPool.random() }.joinToString("")
    }
}