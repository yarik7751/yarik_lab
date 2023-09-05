package com.joy.yariklab.core.api.model.joylove

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UploadedFileRemote(
    @SerialName("filePath")
    val filePath: String,
    @SerialName("fileUrl")
    val fileUrl: String,
)