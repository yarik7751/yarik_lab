package com.joy.yariklab.core.api.service

import com.joy.yariklab.core.api.model.joylove.LoginParamsRemote
import com.joy.yariklab.core.api.model.joylove.RegistrationParamsRemote
import com.joy.yariklab.core.api.model.joylove.UploadedFileRemote
import com.joy.yariklab.core.api.model.joylove.UserTokensRemote
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface JoyLoveRemoteService {

    companion object {
        fun getInstance(retrofit: Retrofit): JoyLoveRemoteService = retrofit.create()
    }

    @POST("login")
    suspend fun login(@Body params: LoginParamsRemote): UserTokensRemote

    @POST("registration")
    suspend fun register(@Body params: RegistrationParamsRemote): UserTokensRemote

    @Multipart
    @POST("uploadavatar")
    suspend fun uploadAvatar(
        @Part image: MultipartBody.Part
    ): UploadedFileRemote

    @Multipart
    @POST("uploadvideo")
    suspend fun uploadVideo(
        @Part image: MultipartBody.Part
    ): UploadedFileRemote
}