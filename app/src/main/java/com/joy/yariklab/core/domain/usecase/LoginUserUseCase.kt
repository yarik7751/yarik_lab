package com.joy.yariklab.core.domain.usecase

import com.joy.yariklab.core.domain.repository.SignInUpRepository
import com.joy.yariklab.core.domain.usecase.base.UseCase

class LoginUserUseCase(
    private val repository: SignInUpRepository,
) : UseCase<LoginUserUseCase.Params, Unit> {

    override suspend fun execute(params: Params) {
        repository.login(
            login = params.login,
            password = params.password
        )
    }

    data class Params(
        val login: String,
        val password: String,
    )
}