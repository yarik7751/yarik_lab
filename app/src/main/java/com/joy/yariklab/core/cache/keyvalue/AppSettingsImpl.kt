package com.joy.yariklab.core.cache.keyvalue

import android.content.SharedPreferences
import com.joy.yariklab.toolskit.EMPTY_STRING

class AppSettingsImpl(
    preferences: SharedPreferences,
) : AppSettings {

    override var currenciesLastUpdateDateStamp: Long by LongPrefDelegate(
        preferences = preferences,
        name = "currenciesLastUpdateDate",
        defaultValue = 0L,
    )

    override var workManagerTasks: String by StringPrefDelegate(
        preferences = preferences,
        name = "workManagerTasks",
        defaultValue = EMPTY_STRING,
    )
}