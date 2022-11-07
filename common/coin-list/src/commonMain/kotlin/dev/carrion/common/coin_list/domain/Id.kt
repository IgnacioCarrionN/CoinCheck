package dev.carrion.common.coin_list.domain

import com.benasher44.uuid.uuid4
import com.benasher44.uuid.uuidFrom
import kotlin.jvm.JvmInline
import kotlin.reflect.KClass

@JvmInline
value class Id(val value: String) {

    init {
        require(validate()) { "Id is empty or blank" }
    }

    private fun validate(): Boolean {
        return value.isNotBlank()
    }

/*  This is an UUID Implementation

    private fun checkValidUUID(): Boolean {
        //return value.isValidUuid()
        return true
    }

    companion object {
        fun newInstance(): Id = Id::class.uuid4Instance()
    }*/

}

private fun KClass<Id>.uuid4Instance(): Id = Id(uuid4().toString())

private fun String.isValidUuid(): Boolean = try {
    uuidFrom(this)
    true
} catch (e: Exception) {
    false
}