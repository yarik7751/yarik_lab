package com.joy.yariklab

import android.app.Application
import com.joy.yariklab.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class YarikLabApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@YarikLabApplication)
            modules(appModule)
        }
    }
}