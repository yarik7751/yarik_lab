package com.joy.yariklab

import android.app.Application
import com.joy.yariklab.di.appModule
import org.koin.core.context.startKoin

class YarikLabApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}