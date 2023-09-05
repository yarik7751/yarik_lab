package com.joy.yariklab.core.domain.interactor

import com.joy.yariklab.core.api.model.joylove.RegistrationParamsRemote
import com.joy.yariklab.core.data.model.joylove.UploadedFile
import com.joy.yariklab.core.domain.repository.SignInUpRepository
import java.io.File

class SignInUpInteractorImpl(
    private val repository: SignInUpRepository,
) : SignInUpInteractor {

    override suspend fun login(login: String, password: String) {
        repository.login(
            login = login,
            password = password,
        )
    }

    override suspend fun register(params: RegistrationParamsRemote) {
        repository.register(params)
    }

    override suspend fun uploadAvatar(file: File): UploadedFile {
        return repository.uploadAvatar(file)
    }
}