package com.joy.yariklab.core.cache.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.joy.yariklab.core.cache.db.entity.CurrencyLocal

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency_local")
    suspend fun getAllCurrencies(): List<CurrencyLocal>

    @Insert
    suspend fun insert(currencyLocal: CurrencyLocal): Long

    @Query("DELETE FROM currency_local")
    suspend fun deleteAll()
}