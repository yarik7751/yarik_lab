package com.joy.yariklab.core.domain.interactor

import com.joy.yariklab.core.data.model.Currency
import com.joy.yariklab.core.data.model.CurrencyDetails
import kotlinx.coroutines.flow.Flow

interface CurrencyInteractor {

    suspend fun subscribeOnCurrencies(): Flow<List<Currency>>

    suspend fun tryToUpdateCurrencies()

    suspend fun getCurrencyByCode(code: String): CurrencyDetails

    suspend fun logWorkManagerTasks(task: String)

    suspend fun isNeedToUpdateCurrencyCache(): Boolean
}