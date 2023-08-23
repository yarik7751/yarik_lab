package com.joy.yariklab.di

import android.content.Context
import android.content.SharedPreferences
import com.joy.yariklab.archkit.DispatchersProvider
import com.joy.yariklab.archkit.DispatchersProviderImpl
import com.joy.yariklab.core.cache.db.CurrenciesDatabase
import com.joy.yariklab.core.cache.keyvalue.AppSettings
import com.joy.yariklab.core.cache.keyvalue.AppSettingsImpl
import com.joy.yariklab.core.data.SignInUpRepositoryImpl
import com.joy.yariklab.core.domain.repository.SignInUpRepository
import com.joy.yariklab.core.domain.usecase.LoginUserUseCase
import com.joy.yariklab.core.local.JoyLoveCache
import com.joy.yariklab.core.local.JoyLoveCacheImpl
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.common.ErrorObserver
import com.joy.yariklab.features.common.ErrorObserverImpl
import com.joy.yariklab.features.login.LoginViewModel
import com.joy.yariklab.features.player.observer.PlayerEmitter
import com.joy.yariklab.features.player.observer.PlayerObserver
import com.joy.yariklab.features.player.observer.PlayerObserverImpl
import com.joy.yariklab.features.registration.RegistrationViewModel
import com.joy.yariklab.features.start.StartViewModel
import com.joy.yariklab.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.binds
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel(
            errorObserver = get(),
        )
    }
    viewModel {
        LoginViewModel(
            loginUserUseCase = get(),
            errorEmitter = get(),
        )
    }
    viewModel {
        RegistrationViewModel(
            errorEmitter = get(),
        )
    }
    viewModel {
        StartViewModel(
            errorEmitter = get(),
        )
    }

    single<SharedPreferences> {
        get<Context>().getSharedPreferences("CURRENCY_APP_PREFERENCES", Context.MODE_PRIVATE)
    }

    single<AppSettings> {
        AppSettingsImpl(
            preferences = get(),
        )
    }

    single<DispatchersProvider> {
        DispatchersProviderImpl()
    }

    single {
        CurrenciesDatabase.newInstance(get())
    }

    single {
        get<CurrenciesDatabase>().currencyDao()
    }

    single {
        get<CurrenciesDatabase>().rateDao()
    }

    single<JoyLoveCache> {
        JoyLoveCacheImpl(
            appSettings = get(),
        )
    }

    single<SignInUpRepository> {
        SignInUpRepositoryImpl(
            dispatchersProvider = get(),
            remoteService = get(),
            joyLoveCache = get(),
        )
    }

    single {
        LoginUserUseCase(
            repository = get(),
        )
    }

    single {
        ErrorObserverImpl()
    }.binds(
        arrayOf(ErrorObserver::class, ErrorEmitter::class)
    )

    single {
        PlayerObserverImpl()
    }.binds(
        arrayOf(PlayerObserver::class, PlayerEmitter::class)
    )
}