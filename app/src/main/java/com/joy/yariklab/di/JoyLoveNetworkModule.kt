package com.joy.yariklab.di

import com.joy.yariklab.core.api.retrofit.getJoyLoveOkHttpClient
import com.joy.yariklab.core.api.retrofit.getJoyLoveRetrofitInstance
import com.joy.yariklab.core.api.retrofit.interceptor.AuthorizationInterceptor
import com.joy.yariklab.core.api.service.JoyLoveRemoteService
import org.koin.dsl.module

val joyLoveNetworkModule = module {

    single {
        AuthorizationInterceptor(
            appSettings = get(),
        )
    }

    single {
        getJoyLoveOkHttpClient(
            authorizationInterceptor = get(),
        )
    }

    single {
        getJoyLoveRetrofitInstance(
            client = get()
        )
    }

    single {
        JoyLoveRemoteService.getInstance(retrofit = get())
    }
}