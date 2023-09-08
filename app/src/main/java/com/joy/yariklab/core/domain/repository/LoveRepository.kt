package com.joy.yariklab.core.domain.repository

import com.joy.yariklab.core.data.model.joylove.UserForLove

interface LoveRepository {

    suspend fun getUsersForLove(): List<UserForLove>
}