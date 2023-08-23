package com.joy.yariklab.di

import com.joy.yariklab.core.api.getOkHttpClient
import com.joy.yariklab.core.api.getRetorfitInstance
import org.koin.dsl.module

val networkModule = module {
    single {
        getOkHttpClient()
    }

    single {
        getRetorfitInstance(
            client = get()
        )
    }
}