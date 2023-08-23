package com.joy.yariklab.core.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

fun getJoyLoveOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()
}

fun getJoyLoveRetrofitInstance(client: OkHttpClient): Retrofit {
    val contentType = "application/json".toMediaType()
    return Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .client(client)
        .addConverterFactory(Json.asConverterFactory(contentType))
        .build()
}