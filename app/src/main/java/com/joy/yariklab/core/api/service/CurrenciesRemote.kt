package com.joy.yariklab.core.api.service

import com.joy.yariklab.core.api.model.CurrencyDetailsRemote
import com.joy.yariklab.core.api.model.CurrencyRemote
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrenciesRemote {

    companion object {
        fun getInstance(retrofit: Retrofit): CurrenciesRemote = retrofit.create()
    }

    @GET("exchangerates/tables/b/last/{lastAmount}")
    suspend fun getCurrencies(
        @Path("lastAmount") lastAmount: Int,
    ): List<CurrencyRemote>

    @GET("exchangerates/rates/b/{currencyCode}/")
    suspend fun getCurrenciesByCode(
        @Path("currencyCode") currencyCode: String,
    ): CurrencyDetailsRemote
}