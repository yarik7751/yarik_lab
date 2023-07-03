package com.joy.yariklab.core.cache.keyvalue

import android.content.SharedPreferences

class AppSettingsImpl(
    preferences: SharedPreferences,
) : AppSettings {

    override var currenciesLastUpdateDateStamp: Long by LongPrefDelegate(
        preferences = preferences,
        name = "currenciesLastUpdateDate",
        defaultValue = 0L,
    )
}