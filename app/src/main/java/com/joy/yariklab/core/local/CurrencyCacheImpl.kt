package com.joy.yariklab.core.local

import com.joy.yariklab.core.cache.InMemoryCache
import com.joy.yariklab.core.cache.keyvalue.AppSettings
import com.joy.yariklab.core.data.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CurrencyCacheImpl(
    private val appSettings: AppSettings,
): CurrencyCache {

    private val stubKey = Any()
    private val inMemoryCache = InMemoryCache<Any, Currency>()

    override var lastUpdateDateStamp: Long?
        get() = appSettings.currenciesLastUpdateDateStamp
        set(value) {
            appSettings.currenciesLastUpdateDateStamp = requireNotNull(value)
        }

    override suspend fun subscribeOnCurrencies(): Flow<List<Currency>> {
        return inMemoryCache.subscribeOn(stubKey).map { it.values }
    }

    override suspend fun saveCurrencies(currencies: List<Currency>) {
        currencies.forEach {
            inMemoryCache.add(stubKey, it)
        }
    }

    override suspend fun clearAllCurrencies() {
        inMemoryCache.clearAll()
    }

    override suspend fun getCurrencies(): List<Currency> {
        return inMemoryCache.subscribeOn(stubKey).map { it.values }.first()
    }

    override suspend fun logWorkManagerTasks(task: String) {
        appSettings.workManagerTasks += task
    }
}