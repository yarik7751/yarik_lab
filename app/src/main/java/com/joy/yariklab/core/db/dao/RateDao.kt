package com.joy.yariklab.core.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.joy.yariklab.core.db.entity.RateLocal

@Dao
interface RateDao {

    /*@Query("SELECT * FROM rate_local")
    suspend fun getAllRates(): List<RateLocal>*/

    @Query("SELECT * FROM rate_local WHERE currencyId=:currencyId")
    suspend fun getRatesByCurrencyId(currencyId: Long): List<RateLocal>

    @Insert
    suspend fun insert(rateLocal: RateLocal): Long

    @Query("DELETE FROM rate_local")
    suspend fun deleteAll()
}