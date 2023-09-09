package com.joy.yariklab.core.api.service

import com.joy.yariklab.core.api.model.joylove.LoginParamsRemote
import com.joy.yariklab.core.api.model.joylove.RegistrationParamsRemote
import com.joy.yariklab.core.api.model.joylove.UploadedFileRemote
import com.joy.yariklab.core.api.model.joylove.UserTokensRemote
import com.joy.yariklab.core.api.model.joylove.user.LikeUserParamsRemote
import com.joy.yariklab.core.api.model.joylove.user.LikeUserResultRemote
import com.joy.yariklab.core.api.model.joylove.user.UserForLoveRemote
import com.joy.yariklab.core.api.retrofit.annotation.UnauthorizedMethod
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface JoyLoveRemoteService {

    companion object {
        fun getInstance(retrofit: Retrofit): JoyLoveRemoteService = retrofit.create()
    }

    val abc: Int

    @UnauthorizedMethod
    @POST("login")
    suspend fun login(@Body params: LoginParamsRemote): UserTokensRemote

    @UnauthorizedMethod
    @POST("registration")
    suspend fun register(@Body params: RegistrationParamsRemote): UserTokensRemote

    @UnauthorizedMethod
    @Multipart
    @POST("uploadavatar")
    suspend fun uploadAvatar(
        @Part image: MultipartBody.Part
    ): UploadedFileRemote

    @UnauthorizedMethod
    @Multipart
    @POST("uploadvideo")
    suspend fun uploadVideo(
        @Part image: MultipartBody.Part
    ): UploadedFileRemote

    @GET("getusersforlove")
    suspend fun getUsersForLove(
        @Query("distance") distance: Int,
        @Query("sex") sex: Int,
        @Query("agemin") ageMin: Int,
        @Query("agemax") ageMax: Int,
    ): List<UserForLoveRemote>

    @POST("likeuser")
    suspend fun likeUser(
        @Body params: LikeUserParamsRemote,
    ): LikeUserResultRemote
}