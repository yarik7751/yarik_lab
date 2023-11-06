package com.joy.yariklab.core.api.model.joylove.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikeUserParamsRemote(
    @SerialName("userForLoveId")
    val userForLoveId: Int,
)
