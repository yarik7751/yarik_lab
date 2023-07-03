package com.joy.yariklab.core.local

import com.joy.yariklab.core.data.model.Currency

interface CurrencyCache {

    suspend fun saveCurrencies(currencies: List<Currency>)

    suspend fun clearAllCurrencies()

    suspend fun getCurrencies(): List<Currency>

    suspend fun getLastUpdateDateStamp(): Long?
    suspend fun setLastUpdateDateStamp(timeStamp: Long)
}