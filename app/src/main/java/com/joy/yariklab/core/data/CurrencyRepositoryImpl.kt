package com.joy.yariklab.core.data

import com.joy.yariklab.archkit.DispatchersProvider
import com.joy.yariklab.core.api.service.CurrenciesRemote
import com.joy.yariklab.core.data.model.CacheUpdateDataParams
import com.joy.yariklab.core.data.model.Currency
import com.joy.yariklab.core.data.model.CurrencyDetails
import com.joy.yariklab.core.domain.repository.CurrencyRepository
import com.joy.yariklab.core.local.CurrencyCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

private const val DEFAULT_LAST_AMOUNT = 6

class CurrencyRepositoryImpl(
    private val dispatchersProvider: DispatchersProvider,
    private val currenciesRemote: CurrenciesRemote,
    private val currencyCache: CurrencyCache,
) : CurrencyRepository {

    override var lastUpdateDateStamp: Long?
        get() = currencyCache.lastUpdateDateStamp
        set(value) {
            currencyCache.lastUpdateDateStamp = value
        }

    override suspend fun subscribeOnCurrencies(): Flow<List<Currency>> {
        return currencyCache.subscribeOnCurrencies()
    }

    override suspend fun tryToUpdateCurrencies(params: CacheUpdateDataParams) {
        withContext(dispatchersProvider.background()) {
            when(params) {
                CacheUpdateDataParams.Leave -> {
                    // none
                }
                is CacheUpdateDataParams.Update -> {
                    currencyCache.apply {
                        clearAllCurrencies()
                    }
                }
            }
            val currencyLocal = currencyCache.getCurrencies()
            currencyLocal.ifEmpty {
                currenciesRemote.getCurrencies(DEFAULT_LAST_AMOUNT).map { remote ->
                    Currency(
                        effectiveDate = remote.effectiveDate.orEmpty(),
                        no = remote.no.orEmpty(),
                        table = remote.table.orEmpty(),
                        rates = remote.rates.orEmpty().map { remoteRate ->
                            Currency.Rate(
                                code = remoteRate.code.orEmpty(),
                                currency = remoteRate.currency.orEmpty(),
                                mid = remoteRate.mid ?: 0.0,
                            )
                        },
                    )
                }.let {
                    if (params is CacheUpdateDataParams.Update) {
                        lastUpdateDateStamp = params.currentTimeStamp
                    }
                    currencyCache.saveCurrencies(it)
                }
            }
        }
    }

    override suspend fun getCurrencyByCode(code: String): CurrencyDetails {
        return withContext(dispatchersProvider.background()) {
            currenciesRemote.getCurrenciesByCode(code).let { currencyRemotes ->
                CurrencyDetails(
                    code = currencyRemotes.code.orEmpty(),
                    country = currencyRemotes.country.orEmpty(),
                    currency = currencyRemotes.currency.orEmpty(),
                    symbol = currencyRemotes.symbol.orEmpty(),
                    table = currencyRemotes.table.orEmpty(),
                    rates = currencyRemotes.rates.orEmpty().map { rate ->
                        CurrencyDetails.Rate(
                            effectiveDate = rate.effectiveDate.orEmpty(),
                            mid = rate.mid ?: 0.0,
                            no = rate.no.orEmpty(),
                        )
                    },
                )
            }
        }
    }

    override suspend fun logWorkManagerTasks(task: String) {
        currencyCache.logWorkManagerTasks(task)
    }
}