package com.joy.yariklab.core.api.retrofit.interceptor

import com.google.common.net.HttpHeaders
import com.joy.yariklab.core.api.retrofit.annotation.UnauthorizedMethod
import com.joy.yariklab.core.api.service.JoyLoveRemoteService
import com.joy.yariklab.core.cache.keyvalue.AppSettings
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import kotlin.reflect.KFunction

class AuthorizationInterceptor(
    private val appSettings: AppSettings,
) : Interceptor {
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        if (isUnauthorizedMethod(request.url.toString())) {
            return chain.proceed(request)
        }
        return request.newBuilder()
            .addHeader(HttpHeaders.AUTHORIZATION, appSettings.token)
            .build().let { newRequest ->
                chain.proceed(newRequest)
            }
    }

    private fun isUnauthorizedMethod(url: String): Boolean {
        val unauthorizedMethods = JoyLoveRemoteService::class.members.toList().filter {
            it is KFunction
        }.filter {
            it.annotations.any { annotation ->
                annotation is UnauthorizedMethod
            }
        }.mapNotNull {
            val urlValues = it.annotations.mapNotNull { annotation ->
                when (annotation) {
                    is GET -> {
                        annotation.value
                    }

                    is POST -> {
                        annotation.value
                    }

                    is PUT -> {
                        annotation.value
                    }

                    is DELETE -> {
                        annotation.value
                    }

                    else -> null
                }
            }

            if (urlValues.size == 1) {
                urlValues.first()
            } else {
                null
            }
        }
        return unauthorizedMethods.any {
            url.contains(it)
        }
    }
}