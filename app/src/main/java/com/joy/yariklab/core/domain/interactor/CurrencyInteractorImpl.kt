package com.joy.yariklab.core.domain.interactor

import com.joy.yariklab.core.data.model.CacheUpdateDataParams
import com.joy.yariklab.core.data.model.Currency
import com.joy.yariklab.core.data.model.CurrencyDetails
import com.joy.yariklab.core.domain.repository.CurrencyRepository

private const val MAX_TIME_DIFF_FOR_DATA_UPDATE = 1000 * 60 * 60 * 12

class CurrencyInteractorImpl(
    private val currencyRepository: CurrencyRepository,
): CurrencyInteractor {

    override suspend fun getCurrencies(): List<Currency> {
        val lastUpdateDateStamp = currencyRepository.getLastUpdateDateStamp()
            ?: return currencyRepository.getCurrencies(CacheUpdateDataParams.Update(System.currentTimeMillis()))

        val diffTime = System.currentTimeMillis() - lastUpdateDateStamp

        val params = when {
            diffTime >= MAX_TIME_DIFF_FOR_DATA_UPDATE -> CacheUpdateDataParams.Update(System.currentTimeMillis())
            else -> CacheUpdateDataParams.Leave
        }
        return currencyRepository.getCurrencies(params)
    }

    override suspend fun getCurrencyByCode(code: String): CurrencyDetails {
        return currencyRepository.getCurrencyByCode(code)
    }
}