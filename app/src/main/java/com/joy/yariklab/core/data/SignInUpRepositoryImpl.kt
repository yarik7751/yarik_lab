package com.joy.yariklab.core.data

import com.joy.yariklab.archkit.DispatchersProvider
import com.joy.yariklab.core.api.model.joylove.LoginParamsRemote
import com.joy.yariklab.core.api.model.joylove.RegistrationParamsRemote
import com.joy.yariklab.core.api.model.joylove.UserTokensRemote
import com.joy.yariklab.core.api.service.JoyLoveRemoteService
import com.joy.yariklab.core.data.model.joylove.UploadedFile
import com.joy.yariklab.core.data.model.joylove.UserTokens
import com.joy.yariklab.core.domain.repository.SignInUpRepository
import com.joy.yariklab.core.local.JoyLoveCache
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class SignInUpRepositoryImpl(
    private val remoteService: JoyLoveRemoteService,
    private val joyLoveCache: JoyLoveCache,
    private val dispatchersProvider: DispatchersProvider,
): SignInUpRepository {

    override suspend fun login(
        login: String,
        password: String,
    ) {
        withContext(dispatchersProvider.background()) {
            remoteService.login(
                LoginParamsRemote(
                    login = login,
                    password = password,
                )
            ).saveTokens()
        }
    }

    override suspend fun register(params: RegistrationParamsRemote) {
        withContext(dispatchersProvider.background()) {
            remoteService.register(params).saveTokens()
        }
    }

    private suspend fun UserTokensRemote.saveTokens() {
        UserTokens(
            token = this.token,
            refreshToken = this.refreshToken,
        ).let {
            joyLoveCache.saveTokens(it)
        }
    }

    override suspend fun uploadAvatar(file: File): UploadedFile {
        return withContext(dispatchersProvider.background()) {
            val imageBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("image", file.name, imageBody)
            val remote = remoteService.uploadAvatar(part)
            UploadedFile(
                filePath = remote.filePath,
                fileUrl = remote.fileUrl,
            )
        }
    }

    override suspend fun uploadVideo(file: File): UploadedFile {
        return withContext(dispatchersProvider.background()) {
            val imageBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val part = MultipartBody.Part.createFormData("video", file.name, imageBody)
            val remote = remoteService.uploadVideo(part)
            UploadedFile(
                filePath = remote.filePath,
                fileUrl = remote.fileUrl,
            )
        }
    }
}