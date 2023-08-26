package com.joy.yariklab.features.registration.model

import androidx.annotation.StringRes
import com.joy.yariklab.R

enum class UserSex(
    val sexId: Int,
    @StringRes
    val titleRes: Int,
) {
    NOT_SELECTED(-1, R.string.sign_up_select_your_sex),
    MALE(0, R.string.sign_up_male),
    FEMALE(1, R.string.sign_up_female),
}