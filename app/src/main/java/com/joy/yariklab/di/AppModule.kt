package com.joy.yariklab.di

import com.joy.yariklab.archkit.DispatchersProvider
import com.joy.yariklab.archkit.DispatchersProviderImpl
import com.joy.yariklab.core.api.getOkHttpClient
import com.joy.yariklab.core.api.getRetorfitInstance
import com.joy.yariklab.core.api.service.CurrenciesService
import com.joy.yariklab.core.data.CurrencyRepositoryImpl
import com.joy.yariklab.core.domain.CurrencyInteractor
import com.joy.yariklab.features.curencydetails.CurrencyDetailsViewModel
import com.joy.yariklab.features.currencieslist.CurrenciesListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        CurrenciesListViewModel(
            currencyInteractor = get(),
        )
    }
    viewModel {
        CurrencyDetailsViewModel()
    }

    single {
        getOkHttpClient()
    }

    single {
        getRetorfitInstance(
            client = get()
        )
    }

    single {
        CurrenciesService.getInstance(retrofit = get())
    }

    single<DispatchersProvider> {
        DispatchersProviderImpl()
    }

    single<CurrencyInteractor> {
        CurrencyRepositoryImpl(
            dispatchersProvider = get(),
            currenciesService = get(),
        )
    }
}