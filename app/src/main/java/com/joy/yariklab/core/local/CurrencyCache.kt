package com.joy.yariklab.core.local

import com.joy.yariklab.core.data.model.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyCache {

    var lastUpdateDateStamp: Long?

    suspend fun subscribeOnCurrencies(): Flow<List<Currency>>

    suspend fun saveCurrencies(currencies: List<Currency>)

    suspend fun clearAllCurrencies()

    suspend fun getCurrencies(): List<Currency>

    suspend fun logWorkManagerTasks(task: String)
}