package com.joy.yariklab.core.api.model.joylove

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserTokensRemote(
    @SerialName("token")
    val token: String,
    @SerialName("refreshToken")
    val refreshToken: String,
)
