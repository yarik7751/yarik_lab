package com.joy.yariklab.features.registration

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joy.yariklab.R
import com.joy.yariklab.archkit.ViewStateDelegate
import com.joy.yariklab.archkit.ViewStateDelegateImpl
import com.joy.yariklab.archkit.safeLaunch
import com.joy.yariklab.core.api.model.joylove.RegistrationParamsRemote
import com.joy.yariklab.core.domain.interactor.SignInUpInteractor
import com.joy.yariklab.features.common.ErrorEmitter
import com.joy.yariklab.features.registration.RegistrationViewModel.Event
import com.joy.yariklab.features.registration.RegistrationViewModel.ViewState
import com.joy.yariklab.features.registration.model.UserSex
import com.joy.yariklab.platformtoolskit.MediaProvider
import com.joy.yariklab.platformtoolskit.ResourceProvider
import com.joy.yariklab.toolskit.DATE_FORMAT_YYYY_MM_DD
import com.joy.yariklab.toolskit.EMPTY_STRING
import com.joy.yariklab.toolskit.dateToString
import kotlinx.coroutines.launch
import java.util.Date

class RegistrationViewModel(
    private val signInUpInteractor: SignInUpInteractor,
    private val errorEmitter: ErrorEmitter,
    private val resourceProvider: ResourceProvider,
    private val mediaProvider: MediaProvider,
) : ViewModel(), ViewStateDelegate<ViewState, Event> by ViewStateDelegateImpl(
    ViewState(
        birthDate = null to resourceProvider.getString(R.string.sign_up_birth_date_label),
        avatarFileName = resourceProvider.getString(R.string.sign_up_birth_logo_label),
        videoFileName = resourceProvider.getString(R.string.sign_up_birth_video_label),
    )
) {

    data class ViewState(
        val isLoading: Boolean = false,
        val name: String = EMPTY_STRING,
        val birthDate: Pair<Date?, String>,
        val avatarPath: String = EMPTY_STRING,
        val avatarFileName: String,
        val avatarUrl: String = EMPTY_STRING,
        val videoPath: String = EMPTY_STRING,
        val videoFileName: String,
        val videoUrl: String = EMPTY_STRING,
        val password: String = EMPTY_STRING,
        val email: String = EMPTY_STRING,
        val mobilePhone: String = EMPTY_STRING,
        val sex: UserSex = UserSex.NOT_SELECTED,

        )

    sealed interface Event {
        object GoToUserList : Event
        data class ShowValidationErrorDialog(val message: String) : Event
        object ShowDataPickerDialog : Event
    }

    fun onImageSelect(uri: Uri) {
        viewModelScope.safeLaunch(errorEmitter::emit) {
            reduce {
                it.copy(isLoading = true)
            }
            val imageFile = mediaProvider.getMediaFileFromUri(uri)
            val imagePath = signInUpInteractor.uploadAvatar(imageFile)
            reduce {
                it.copy(
                    avatarPath = imagePath.filePath,
                    avatarUrl = imagePath.fileUrl,
                    avatarFileName = imageFile.name,
                )
            }
        }.invokeOnCompletion {
            viewModelScope.reduce {
                it.copy(isLoading = false)
            }
        }
    }

    fun onVideoSelect(uri: Uri) {
        viewModelScope.safeLaunch(errorEmitter::emit) {
            reduce {
                it.copy(isLoading = true)
            }
            val videoFile = mediaProvider.getMediaFileFromUri(uri)
            val imagePath = signInUpInteractor.uploadVideo(videoFile)
            reduce {
                it.copy(
                    videoPath = imagePath.filePath,
                    videoUrl = imagePath.fileUrl,
                    videoFileName = videoFile.name,
                )
            }
        }.invokeOnCompletion {
            viewModelScope.reduce {
                it.copy(isLoading = false)
            }
        }
    }

    fun onNameChanged(name: String) {
        viewModelScope.reduce {
            it.copy(name = name)
        }
    }

    fun onBirthDateAction() {
        viewModelScope.sendEvent(Event.ShowDataPickerDialog)
    }

    fun onBirthDateChanged(timestamp: Long) {
        viewModelScope.launch {
            val birthDate = Date(timestamp)
            val birthDateText = birthDate.dateToString(DATE_FORMAT_YYYY_MM_DD)
            reduce {
                it.copy(
                    birthDate = birthDate to birthDateText,
                )
            }
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
            it.copy(mobilePhone = phone)
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
            validateRegistrationData()?.let { throw it }
            reduce {
                it.copy(isLoading = true)
            }

            val birthDate = requireNotNull(stateValue.birthDate.first)
            signInUpInteractor.register(
                RegistrationParamsRemote(
                    birthDate = birthDate.dateToString(DATE_FORMAT_YYYY_MM_DD),
                    contacts = EMPTY_STRING,
                    email = stateValue.email,
                    latitude = 0F,
                    longitude = 0F,
                    logoPath = stateValue.avatarPath,
                    videoPath = stateValue.videoPath,
                    mobilePhone = stateValue.mobilePhone,
                    name = stateValue.name,
                    password = stateValue.password,
                    registrationDate = Date().dateToString(DATE_FORMAT_YYYY_MM_DD),
                    sex = stateValue.sex.sexId,
                )
            )

            sendEvent(Event.GoToUserList)
        }.invokeOnCompletion {
            viewModelScope.reduce {
                it.copy(isLoading = false)
            }
        }
    }

    private fun validateRegistrationData(): Throwable? {
        val message = when {
            stateValue.name.isBlank() -> {
                resourceProvider.getString(R.string.sign_up_name_error)
            }
            stateValue.birthDate.first == null -> {
                resourceProvider.getString(R.string.sign_up_birth_date_error)
            }
            stateValue.avatarPath.isBlank() -> {
                resourceProvider.getString(R.string.sign_up_logo_error)
            }
            stateValue.videoPath.isBlank() -> {
                resourceProvider.getString(R.string.sign_up_video_error)
            }
            stateValue.password.isBlank() -> {
                resourceProvider.getString(R.string.sign_up_password_error)
            }
            stateValue.email.isBlank() -> {
                resourceProvider.getString(R.string.sign_up_email_error)
            }
            stateValue.mobilePhone.isBlank() -> {
                resourceProvider.getString(R.string.sign_up_phone_error)
            }
            stateValue.sex == UserSex.NOT_SELECTED -> {
                resourceProvider.getString(R.string.sign_up_sex_error)
            }
            else -> return null
        }

        return RuntimeException(message)
    }
}