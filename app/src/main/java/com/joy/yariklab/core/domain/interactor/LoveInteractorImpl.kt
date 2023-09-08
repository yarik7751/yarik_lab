package com.joy.yariklab.core.domain.interactor

import com.joy.yariklab.core.data.model.joylove.UserForLove
import com.joy.yariklab.core.domain.repository.LoveRepository

class LoveInteractorImpl(
    private val repository: LoveRepository,
): LoveInteractor {

    override suspend fun getUsersForLove(): List<UserForLove> {
        return repository.getUsersForLove()
    }
}