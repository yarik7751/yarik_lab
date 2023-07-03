package com.joy.yariklab.di

import com.joy.yariklab.archkit.DispatchersProvider
import com.joy.yariklab.archkit.DispatchersProviderImpl
import com.joy.yariklab.core.api.getOkHttpClient
import com.joy.yariklab.core.api.getRetorfitInstance
import com.joy.yariklab.core.api.service.CurrenciesRemote
import com.joy.yariklab.core.data.CurrencyRepositoryImpl
import com.joy.yariklab.core.db.CurrenciesDatabase
import com.joy.yariklab.core.domain.interactor.CurrencyInteractor
import com.joy.yariklab.core.domain.interactor.CurrencyInteractorImpl
import com.joy.yariklab.core.domain.repository.CurrencyRepository
import com.joy.yariklab.core.local.CurrencyCache
import com.joy.yariklab.core.local.CurrencyCacheImpl
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
        CurrencyDetailsViewModel(
            currencyCode = it.get(),
            currencyInteractor = get(),
        )
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
        CurrenciesRemote.getInstance(retrofit = get())
    }

    single<DispatchersProvider> {
        DispatchersProviderImpl()
    }

    single<CurrencyRepository> {
        CurrencyRepositoryImpl(
            dispatchersProvider = get(),
            currenciesRemote = get(),
            currencyCache = get(),
        )
    }

    single<CurrencyInteractor> {
        CurrencyInteractorImpl(
            currencyRepository = get(),
        )
    }

    single<CurrencyCache> {
        CurrencyCacheImpl(
            currencyDao = get(),
            rateDao = get(),
        )
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
}