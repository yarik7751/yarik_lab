package com.joy.yariklab.core.api

import com.google.gson.GsonBuilder
import com.joy.yariklab.toolskit.EMPTY_STRING
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

fun getOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()
}

fun getRetorfitInstance(client: OkHttpClient): Retrofit {
    val gson: GsonConverterFactory = GsonConverterFactory.create(
        GsonBuilder()
            .create()
    )

    val nullOnEmpty = object : Converter.Factory() {
        fun converterFactory() = this
        override fun responseBodyConverter(
            type: Type,
            annotations: Array<out Annotation>,
            retrofit: Retrofit
        ) = object : Converter<ResponseBody, Any?> {
            val nextResponseBodyConverter =
                retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

            override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) {
                nextResponseBodyConverter.convert(value)
            } else {
                EMPTY_STRING
            }
        }
    }
    return Retrofit.Builder()
        .baseUrl("https://api.nbp.pl/api/")
        .client(client)
        .addConverterFactory(nullOnEmpty)
        .addConverterFactory(gson)
        .build()
}