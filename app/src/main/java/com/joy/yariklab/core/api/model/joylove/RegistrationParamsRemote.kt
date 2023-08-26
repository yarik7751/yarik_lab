package com.joy.yariklab.core.api.model.joylove

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationParamsRemote(
    @SerialName("birthDate")
    val birthDate: String,
    @SerialName("contacts")
    val contacts: String,
    @SerialName("email")
    val email: String,
    @SerialName("latitude")
    val latitude: Float,
    @SerialName("longitude")
    val longitude: Float,
    @SerialName("logoPath")
    val logoPath: String,
    @SerialName("mobilePhone")
    val mobilePhone: String,
    @SerialName("name")
    val name: String,
    @SerialName("password")
    val password: String,
    @SerialName("registrationDate")
    val registrationDate: String,
    @SerialName("sex")
    val sex: Int,
    @SerialName("videoPath")
    val videoPath: String
)