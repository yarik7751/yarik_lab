package com.joy.yariklab.core.domain.interactor

import com.joy.yariklab.core.data.model.joylove.UserForLove

interface LoveInteractor {

    suspend fun getUsersForLove(): List<UserForLove>
}