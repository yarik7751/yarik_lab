package com.joy.yariklab.core.cache.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_local")
data class CurrencyLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val effectiveDate: String,
    val no: String,
    val table: String,
)

@Entity(tableName = "rate_local")
data class RateLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val currencyId: Long,
    val code: String,
    val currency: String,
    val mid: Double,
)