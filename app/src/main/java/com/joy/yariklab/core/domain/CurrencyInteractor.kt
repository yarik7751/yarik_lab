package com.joy.yariklab.core.domain

import com.joy.yariklab.core.data.model.Currency
import com.joy.yariklab.core.data.model.CurrencyDetails

interface CurrencyInteractor {

    suspend fun getCurrencies(): List<Currency>

    suspend fun getCurrencyByCode(code: String): CurrencyDetails
}