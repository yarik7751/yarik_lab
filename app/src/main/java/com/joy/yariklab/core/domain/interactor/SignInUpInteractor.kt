package com.joy.yariklab.core.domain.interactor

import com.joy.yariklab.core.api.model.joylove.RegistrationParamsRemote

interface SignInUpInteractor {

    suspend fun login(
        login: String,
        password: String,
    )

    suspend fun register(params: RegistrationParamsRemote)
}