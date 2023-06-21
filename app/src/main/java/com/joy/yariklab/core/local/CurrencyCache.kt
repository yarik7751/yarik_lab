package com.joy.yariklab.core.local

import com.joy.yariklab.core.data.model.Currency

interface CurrencyCache {

    suspend fun saveCurrencies(currencies: List<Currency>)

    suspend fun getCurrencies(): List<Currency>
}