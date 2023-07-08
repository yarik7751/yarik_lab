package com.joy.yariklab.core.domain.interactor

import com.joy.yariklab.core.data.model.CacheUpdateDataParams
import com.joy.yariklab.core.data.model.Currency
import com.joy.yariklab.core.data.model.CurrencyDetails
import com.joy.yariklab.core.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private const val MAX_TIME_DIFF_FOR_DATA_UPDATE = 1000 * 60 * 60 * 12

class CurrencyInteractorImpl(
    private val currencyRepository: CurrencyRepository,
): CurrencyInteractor {

    private val mutex = Mutex()

    override suspend fun subscribeOnCurrencies(): Flow<List<Currency>> {
        return currencyRepository.subscribeOnCurrencies()
    }

    override suspend fun tryToUpdateCurrencies() {
        mutex.withLock {
            val isNeedToUpdateCurrencyCache = isNeedToUpdateCurrencyCache()
            val params = when {
                isNeedToUpdateCurrencyCache -> CacheUpdateDataParams.Update(System.currentTimeMillis())
                else -> CacheUpdateDataParams.Leave
            }

            currencyRepository.tryToUpdateCurrencies(params)
        }
    }

    override suspend fun getCurrencyByCode(code: String): CurrencyDetails {
        return currencyRepository.getCurrencyByCode(code)
    }

    override suspend fun logWorkManagerTasks(task: String) {
        currencyRepository.logWorkManagerTasks(task)
    }

    override suspend fun isNeedToUpdateCurrencyCache(): Boolean {
        val lastUpdateDateStamp = currencyRepository.lastUpdateDateStamp ?: return true
        val diffTime = System.currentTimeMillis() - lastUpdateDateStamp
        val result = diffTime >= MAX_TIME_DIFF_FOR_DATA_UPDATE
        if (result.not()) {
            currencyRepository.lastUpdateDateStamp = System.currentTimeMillis()
        }
        return result
    }
}