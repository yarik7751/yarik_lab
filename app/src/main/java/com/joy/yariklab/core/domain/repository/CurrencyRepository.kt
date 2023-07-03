package com.joy.yariklab.core.domain.repository

import com.joy.yariklab.core.data.model.CacheUpdateDataParams
import com.joy.yariklab.core.data.model.Currency
import com.joy.yariklab.core.data.model.CurrencyDetails

interface CurrencyRepository {


    suspend fun getCurrencies(params: CacheUpdateDataParams): List<Currency>

    suspend fun getCurrencyByCode(code: String): CurrencyDetails

    suspend fun getLastUpdateDateStamp(): Long?
}