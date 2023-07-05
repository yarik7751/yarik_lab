package com.joy.yariklab.core.local

import com.joy.yariklab.core.data.model.Currency

interface CurrencyCache {

    var lastUpdateDateStamp: Long?

    suspend fun saveCurrencies(currencies: List<Currency>)

    suspend fun clearAllCurrencies()

    suspend fun getCurrencies(): List<Currency>

    suspend fun logWorkManagerTasks(task: String)
}