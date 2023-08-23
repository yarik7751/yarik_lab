package com.joy.yariklab.core.api.model.joylove

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginParamsRemote(
    @SerialName("login")
    val login: String,
    @SerialName("password")
    val password: String,
)
