package com.joy.yariklab.core.domain.interactor

import com.joy.yariklab.core.api.model.joylove.RegistrationParamsRemote
import com.joy.yariklab.core.data.model.joylove.UploadedFile
import java.io.File

interface SignInUpInteractor {

    suspend fun login(
        login: String,
        password: String,
    )

    suspend fun register(params: RegistrationParamsRemote)

    suspend fun uploadAvatar(file: File): UploadedFile
}