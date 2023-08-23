package com.joy.yariklab.di

import com.joy.yariklab.core.api.getJoyLoveOkHttpClient
import com.joy.yariklab.core.api.getJoyLoveRetrofitInstance
import com.joy.yariklab.core.api.service.JoyLoveRemoteService
import org.koin.dsl.module

val joyLoveNetworkModule = module {

    single {
        getJoyLoveOkHttpClient()
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