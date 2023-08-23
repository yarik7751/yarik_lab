package com.joy.yariklab.core.local

import com.joy.yariklab.core.cache.keyvalue.AppSettings
import com.joy.yariklab.core.data.model.joylove.UserTokens

class JoyLoveCacheImpl(
    private val appSettings: AppSettings,
): JoyLoveCache {

    override suspend fun saveTokens(userTokens: UserTokens) {
        appSettings.token = userTokens.token
        appSettings.refreshToken = userTokens.refreshToken
    }
}