package com.joy.yariklab.core.local

import com.joy.yariklab.core.data.model.joylove.UserTokens

interface JoyLoveCache {

    suspend fun saveTokens(userTokens: UserTokens)
}