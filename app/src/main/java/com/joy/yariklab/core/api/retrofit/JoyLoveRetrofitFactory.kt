package com.joy.yariklab.core.api.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.joy.yariklab.core.api.retrofit.interceptor.AuthorizationInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val DEFAULT_TIMEOUT_SECONDS = 60L

fun getJoyLoveOkHttpClient(
    authorizationInterceptor: AuthorizationInterceptor,
): OkHttpClient {
    return OkHttpClient.Builder()
        .callTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .addInterceptor(authorizationInterceptor)
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