package com.joy.yariklab.features.userlist.model

data class UserForLoveUi(
    val id: Int,
    val age: Int,
    val contacts: String,
    val email: String,
    val logoUrl: String,
    val videoUrl: String,
    val mobilePhone: String,
    val name: String,
    val rating: Double,
    val sex: Int,
    val isCurrent: Boolean,
    val isLiked: Boolean,
)
