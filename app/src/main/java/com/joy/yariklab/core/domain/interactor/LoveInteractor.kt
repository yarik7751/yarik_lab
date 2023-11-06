package com.joy.yariklab.core.domain.interactor

import com.joy.yariklab.core.data.model.joylove.LikeUserResult
import com.joy.yariklab.core.data.model.joylove.UserForLove

interface LoveInteractor {

    suspend fun getUsersForLove(): List<UserForLove>

    suspend fun likeUser(likeUserId: Int): LikeUserResult
}