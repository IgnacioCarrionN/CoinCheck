package dev.carrion.common.coin_list.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class IdTest {

    @Test
    fun `should create valid Id`() {
        val uuid = "17a22168-a9d5-47c4-8cd5-716f26b4dc68"
        val id = Id(uuid)
        assertEquals(id.value, uuid)
    }

    @Test
    fun `should throw domain exception on empty Id`() {
        val id = ""
        assertFailsWith(DomainException::class, "Id is empty or blank") {
            Id(id)
        }
    }

    @Test
    fun `should throw domain exception on blank Id`() {
        val id = " "
        assertFailsWith(DomainException::class, "Id is empty or blank") {
            Id(id)
        }
    }

}