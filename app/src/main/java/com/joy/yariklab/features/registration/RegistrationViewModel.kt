package com.joy.yariklab.features.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.archkit.safeLaunch
import com.joy.yariklab.core.api.model.joylove.RegistrationParamsRemote
import com.joy.yariklab.core.domain.interactor.SignInUpInteractor
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.registration.RegistrationViewModel.Event
import com.joy.yariklab.features.registration.RegistrationViewModel.ViewState
import com.joy.yariklab.features.registration.model.UserSex
import com.joy.yariklab.toolskit.DATE_FORMAT_YYYY_MM_DD
import com.joy.yariklab.toolskit.EMPTY_STRING
import com.joy.yariklab.toolskit.dateToString
import java.util.Date

class RegistrationViewModel(
    private val signInUpInteractor: SignInUpInteractor,
    private val errorEmitter: ErrorEmitter,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(ViewState()) {

    data class ViewState(
        val isLoading: Boolean = false,
        val name: String = EMPTY_STRING,
        val birthDate: Date? = null,
        val avatarPath: String = EMPTY_STRING,
        val videoPath: String = EMPTY_STRING,
        val password: String = EMPTY_STRING,
        val email: String = EMPTY_STRING,
        val phone: String = EMPTY_STRING,
        val sex: UserSex = UserSex.NOT_SELECTED,

    )

    sealed interface Event {
        data class ShowValidationErrorDialog(val message: String) : Event
    }

    fun onNameChanged(name: String) {
        viewModelScope.reduce {
            it.copy(name = name)
        }
    }

    fun onPasswordChanged(password: String) {
        viewModelScope.reduce {
            it.copy(password = password)
        }
    }

    fun onEmailChanged(email: String) {
        viewModelScope.reduce {
            it.copy(email = email)
        }
    }

    fun onPhoneChanged(phone: String) {
        viewModelScope.reduce {
            it.copy(phone = phone)
        }
    }

    fun onSexChanged(check: Boolean) {
        viewModelScope.reduce {
            it.copy(sex = when {
                check -> UserSex.FEMALE
                else -> UserSex.MALE
            })
        }
    }

    fun onRegistrationAction() {
        viewModelScope.safeLaunch(errorEmitter::emit) {
            reduce {
                it.copy(isLoading = true)
            }

            signInUpInteractor.register(
                RegistrationParamsRemote(
                    birthDate = "1994-07-29",
                    contacts = EMPTY_STRING,
                    email = stateValue.email,
                    latitude = 0F,
                    longitude = 0F,
                    logoPath = /*tateValue.avatarPath*/"avaters/testavatar",
                    videoPath = /*stateValue.videoPath*/"videos/testvideo",
                    mobilePhone = stateValue.phone,
                    name = stateValue.name,
                    password = stateValue.password,
                    registrationDate = Date().dateToString(DATE_FORMAT_YYYY_MM_DD),
                    sex = stateValue.sex.sexId,
                )
            )
        }.invokeOnCompletion {
            viewModelScope.reduce {
                it.copy(isLoading = false)
            }
        }
    }
}