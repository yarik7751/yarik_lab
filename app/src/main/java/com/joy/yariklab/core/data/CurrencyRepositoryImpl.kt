package com.joy.yariklab.core.data

import com.joy.yariklab.archkit.DispatchersProvider
import com.joy.yariklab.core.api.service.CurrenciesRemote
import com.joy.yariklab.core.data.model.Currency
import com.joy.yariklab.core.data.model.CurrencyDetails
import com.joy.yariklab.core.domain.CurrencyInteractor
import com.joy.yariklab.core.local.CurrencyCache
import kotlinx.coroutines.withContext

private const val DEFAULT_LAST_AMOUNT = 6

class CurrencyRepositoryImpl(
    private val dispatchersProvider: DispatchersProvider,
    private val currenciesRemote: CurrenciesRemote,
    private val currencyCache: CurrencyCache,
) : CurrencyInteractor {

    override suspend fun getCurrencies(): List<Currency> {
        return withContext(dispatchersProvider.background()) {
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
                    currencyCache.saveCurrencies(it)
                    it
                }
            }
        }
    }

    override suspend fun getCurrencyByCode(code: String): CurrencyDetails {
        return currenciesRemote.getCurrenciesByCode(code).let { currencyRemotes ->
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