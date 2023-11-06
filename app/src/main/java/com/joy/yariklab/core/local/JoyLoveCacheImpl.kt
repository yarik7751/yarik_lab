package com.joy.yariklab.core.local

import com.joy.yariklab.core.cache.keyvalue.AppSettings
import com.joy.yariklab.core.data.model.joylove.UserTokens

class JoyLoveCacheImpl(
    private val appSettings: AppSettings,
): JoyLoveCache {

    override var tokens: UserTokens
        get() = UserTokens(
            token = appSettings.token,
            refreshToken = appSettings.refreshToken,
        )
        set(value) {
            appSettings.token = value.token
            appSettings.refreshToken = value.refreshToken
        }
}