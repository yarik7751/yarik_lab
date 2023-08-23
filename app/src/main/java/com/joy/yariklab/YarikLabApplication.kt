package com.joy.yariklab

import android.app.Application
import com.joy.yariklab.di.appModule
import com.joy.yariklab.di.joyLoveNetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

private const val PERIOD_INTERVAL = 16L

class YarikLabApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@YarikLabApplication)
            workManagerFactory()
            modules(appModule, joyLoveNetworkModule)
        }
    }
}