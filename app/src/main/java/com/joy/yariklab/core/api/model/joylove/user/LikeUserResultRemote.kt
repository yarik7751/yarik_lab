package com.joy.yariklab.core.api.model.joylove.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LikeUserResultRemote(
    @SerialName("isMeetingConfirmed")
    val isMeetingConfirmed: Boolean,
    @SerialName("isMeetingCreated")
    val isMeetingCreated: Boolean
)