package com.joy.yariklab.core.api.service

import com.joy.yariklab.core.api.model.joylove.LoginParamsRemote
import com.joy.yariklab.core.api.model.joylove.RegistrationParamsRemote
import com.joy.yariklab.core.api.model.joylove.UserTokensRemote
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST

interface JoyLoveRemoteService {

    companion object {
        fun getInstance(retrofit: Retrofit): JoyLoveRemoteService = retrofit.create()
    }

    @POST("login")
    suspend fun login(@Body params: LoginParamsRemote): UserTokensRemote

    @POST("registration")
    suspend fun register(@Body params: RegistrationParamsRemote): UserTokensRemote
}