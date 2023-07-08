package com.joy.yariklab.core.domain.repository

import com.joy.yariklab.core.data.model.CacheUpdateDataParams
import com.joy.yariklab.core.data.model.Currency
import com.joy.yariklab.core.data.model.CurrencyDetails
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    var lastUpdateDateStamp: Long?

    suspend fun subscribeOnCurrencies(): Flow<List<Currency>>

    suspend fun tryToUpdateCurrencies(params: CacheUpdateDataParams)

    suspend fun getCurrencyByCode(code: String): CurrencyDetails

    suspend fun logWorkManagerTasks(task: String)
}