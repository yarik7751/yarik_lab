package com.joy.yariklab.core.domain

import com.joy.yariklab.core.data.model.Currency

interface CurrencyInteractor {

    suspend fun getCurrencies(): List<Currency>
}