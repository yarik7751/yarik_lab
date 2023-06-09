package com.joy.yariklab.core.data

import com.joy.yariklab.archkit.DispatchersProvider
import com.joy.yariklab.core.api.service.CurrenciesService
import com.joy.yariklab.core.data.model.Currency
import com.joy.yariklab.core.domain.CurrencyInteractor
import kotlinx.coroutines.withContext

private const val DEFAULT_LAST_AMOUNT = 6

class CurrencyRepositoryImpl(
    private val dispatchersProvider: DispatchersProvider,
    private val currenciesService: CurrenciesService,
) : CurrencyInteractor {

    override suspend fun getCurrencies(): List<Currency> {
        return withContext(dispatchersProvider.background()) {
            currenciesService.getCurrencies(DEFAULT_LAST_AMOUNT).map { remote ->
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
            }
        }
    }
}