package com.joy.yariklab.core.data.model

sealed interface CacheUpdateDataParams {

    data class Update(val currentTimeStamp: Long) : CacheUpdateDataParams

    object Leave : CacheUpdateDataParams
}