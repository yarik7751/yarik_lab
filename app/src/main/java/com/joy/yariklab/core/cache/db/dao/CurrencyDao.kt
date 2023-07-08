package com.joy.yariklab.core.cache.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.joy.yariklab.core.cache.db.entity.CurrencyLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currency_local")
    suspend fun getAllCurrencies(): List<CurrencyLocal>

    @Query("SELECT * FROM currency_local")
    fun subscribeOnCurrencies(): Flow<List<CurrencyLocal>>

    @Insert
    suspend fun insert(currencyLocal: CurrencyLocal): Long

    @Query("DELETE FROM currency_local")
    suspend fun deleteAll()
}