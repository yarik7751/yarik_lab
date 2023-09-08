package com.joy.yariklab.core.domain.usecase

import com.joy.yariklab.core.domain.repository.SignInUpRepository
import com.joy.yariklab.core.domain.usecase.base.UseCase

class IsUserAuthorizedUseCase(
    private val repository: SignInUpRepository,
) : UseCase<Unit, Boolean> {

    override suspend fun execute(params: Unit): Boolean {
        return repository.isUserAuthorized
    }
}