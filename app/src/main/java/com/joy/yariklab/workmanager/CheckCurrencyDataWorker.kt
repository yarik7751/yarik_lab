package com.joy.yariklab.workmanager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.joy.yariklab.core.domain.interactor.CurrencyInteractor

class CheckCurrencyDataWorker(
    context: Context,
    params: WorkerParameters,
    private val currencyInteractor: CurrencyInteractor,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // TODO with in-memory cache it is impossible
        /*currencyInteractor.apply {
            tryToUpdateCurrencies()
            logWorkManagerTasks("Current time: ${System.currentTimeMillis()}")
        }*/
        return Result.success()
    }
}