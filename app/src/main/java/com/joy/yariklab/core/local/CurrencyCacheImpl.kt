package com.joy.yariklab.core.local

import com.joy.yariklab.core.cache.db.dao.CurrencyDao
import com.joy.yariklab.core.cache.db.dao.RateDao
import com.joy.yariklab.core.cache.db.entity.CurrencyLocal
import com.joy.yariklab.core.cache.db.entity.RateLocal
import com.joy.yariklab.core.cache.keyvalue.AppSettings
import com.joy.yariklab.core.data.model.Currency

class CurrencyCacheImpl(
    private val appSettings: AppSettings,
    private val currencyDao: CurrencyDao,
    private val rateDao: RateDao,
): CurrencyCache {

    override var lastUpdateDateStamp: Long?
        get() = appSettings.currenciesLastUpdateDateStamp
        set(value) {
            appSettings.currenciesLastUpdateDateStamp = requireNotNull(value)
        }

    override suspend fun saveCurrencies(currencies: List<Currency>) {
        currencies.forEach { currency ->
            val currencyId = currencyDao.insert(
                CurrencyLocal(
                    id = 0,
                    effectiveDate = currency.effectiveDate,
                    no = currency.no,
                    table = currency.table,
                )
            )

            currency.rates.forEach { rate ->
                rateDao.insert(
                    RateLocal(
                        id = 0,
                        currencyId = currencyId,
                        code = rate.code,
                        currency = rate.currency,
                        mid = rate.mid,
                    )
                )
            }
        }
    }

    override suspend fun clearAllCurrencies() {
        currencyDao.deleteAll()
    }

    override suspend fun getCurrencies(): List<Currency> {
        return currencyDao.getAllCurrencies().map { currencyLocal ->
            val rates = rateDao.getRatesByCurrencyId(currencyLocal.id).map { rateLocal ->
                Currency.Rate(
                    code = rateLocal.code,
                    currency = rateLocal.currency,
                    mid = rateLocal.mid,
                )
            }
            Currency(
                effectiveDate = currencyLocal.effectiveDate,
                no = currencyLocal.no,
                rates = rates,
                table = currencyLocal.table,
            )
        }
    }

    override suspend fun logWorkManagerTasks(task: String) {
        appSettings.workManagerTasks += task
    }
}