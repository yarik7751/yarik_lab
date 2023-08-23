package com.joy.yariklab

import android.app.Application
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.joy.yariklab.di.appModule
import com.joy.yariklab.di.joyLoveNetworkModule
import com.joy.yariklab.di.networkModule
import com.joy.yariklab.main.IS_TEST_APP
import com.joy.yariklab.workmanager.CheckCurrencyDataWorker
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

private const val PERIOD_INTERVAL = 16L

class YarikLabApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val networkModule = if (IS_TEST_APP) {
            networkModule
        } else {
            joyLoveNetworkModule
        }
        startKoin {
            androidContext(this@YarikLabApplication)
            workManagerFactory()
            modules(appModule, networkModule)
        }

        val workRequest: WorkRequest =
            PeriodicWorkRequestBuilder<CheckCurrencyDataWorker>(PERIOD_INTERVAL, TimeUnit.MINUTES)
                .build()

        WorkManager
            .getInstance(this)
            .enqueue(workRequest)
    }
}