package com.joy.yariklab.core.domain.interactor

import com.joy.yariklab.core.data.model.Currency
import com.joy.yariklab.core.data.model.CurrencyDetails
import com.joy.yariklab.core.domain.repository.CurrencyRepository

class CurrencyInteractorImpl(
    private val currencyRepository: CurrencyRepository,
): CurrencyInteractor {

    override suspend fun getCurrencies(): List<Currency> {
        return currencyRepository.getCurrencies()
    }

    override suspend fun getCurrencyByCode(code: String): CurrencyDetails {
        return currencyRepository.getCurrencyByCode(code)
    }
}