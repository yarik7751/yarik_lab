package com.joy.yariklab.core.domain.repository

import com.joy.yariklab.core.api.model.joylove.RegistrationParamsRemote

interface SignInUpRepository {

    suspend fun login(
        login: String,
        password: String,
    )

    suspend fun register(params: RegistrationParamsRemote)
}