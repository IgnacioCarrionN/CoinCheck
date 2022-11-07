package dev.carrion.database.infrastructure

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dev.carrion.sqldelight.database.CoinCheck

actual class DriverFactorySQLDelight(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(CoinCheck.Schema, context, "coin.db")
    }
}

