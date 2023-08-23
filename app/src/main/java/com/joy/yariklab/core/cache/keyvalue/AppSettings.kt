package com.joy.yariklab.core.cache.keyvalue

interface AppSettings {

    var currenciesLastUpdateDateStamp: Long

    var workManagerTasks: String

    var token: String

    var refreshToken: String
}