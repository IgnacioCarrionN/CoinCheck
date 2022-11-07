package dev.carrion.database.infrastructure

import com.squareup.sqldelight.db.SqlDriver

expect class DriverFactorySQLDelight {
    fun createDriver(): SqlDriver
}