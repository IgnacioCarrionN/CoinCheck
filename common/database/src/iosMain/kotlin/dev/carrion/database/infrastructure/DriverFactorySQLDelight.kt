package dev.carrion.database.infrastructure

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import dev.carrion.sqldelight.database.CoinCheck

actual class DriverFactorySQLDelight() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(CoinCheck.Schema, "test.db")
    }
}