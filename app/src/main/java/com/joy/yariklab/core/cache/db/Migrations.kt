package com.joy.yariklab.core.cache.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val migrationFrom1To2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("DROP TABLE IF EXISTS currency_local")
        db.execSQL("DROP TABLE IF EXISTS rate_local")
    }
}