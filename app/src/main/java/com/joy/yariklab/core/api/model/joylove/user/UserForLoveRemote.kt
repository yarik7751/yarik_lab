package com.joy.yariklab.core.api.model.joylove.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserForLoveRemote(
    @SerialName("id")
    val id: Int,
    @SerialName("age")
    val age: Int,
    @SerialName("contacts")
    val contacts: String?,
    @SerialName("email")
    val email: String,
    @SerialName("logoUrl")
    val logoUrl: String,
    @SerialName("videoUrl")
    val videoUrl: String,
    @SerialName("mobilePhone")
    val mobilePhone: String,
    @SerialName("name")
    val name: String,
    @SerialName("rating")
    val rating: Double,
    @SerialName("sex")
    val sex: Int,
)