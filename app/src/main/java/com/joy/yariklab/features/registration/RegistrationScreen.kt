package com.joy.yariklab.features.registration

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joy.yariklab.R
import com.joy.yariklab.features.common.logo.StartTitle
import com.joy.yariklab.features.registration.RegistrationViewModel.Event
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.toolskit.EMPTY_STRING
import com.joy.yariklab.ui.theme.DefaultButton
import com.joy.yariklab.ui.theme.DefaultTextField
import com.joy.yariklab.ui.theme.Magenta
import com.joy.yariklab.ui.theme.Pink80
import com.joy.yariklab.uikit.LabProgressBar
import com.joy.yariklab.uikit.simplePadding
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    flowCoordinator: FlowCoordinator,
) {
    val state = viewModel.viewState.collectAsState()
    val openDialog = remember { mutableStateOf(false) }
    val openImagePicker = remember { mutableStateOf(false) }
    val openVideoPicker = remember { mutableStateOf(false) }

    /*DisposableEffect(key1 = viewModel) {
        viewModel.hashCode()
        onDispose {
            viewModel.hashCode()
        }
    }*/

    if (state.value.isLoading) {
        LabProgressBar()
    }

    RegistrationInfo(
        viewModel = viewModel,
        state = state.value,
        openImagePicker = openImagePicker,
        openVideoPicker = openVideoPicker,
    )

    DateDialog(
        openDialog = openDialog,
        viewModel = viewModel,
    )

    OpenImagePicker(openImagePicker)
    OpenVideoPicker(openVideoPicker)

    LaunchedEffect(key1 = Unit) {
        viewModel.singleEvents.collectLatest { event ->
            when (event) {
                Event.GoToUserList -> {
                    flowCoordinator.goToUserList()
                }

                is Event.ShowValidationErrorDialog -> TODO()
                Event.ShowDataPickerDialog -> {
                    openDialog.value = true
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    openDialog: MutableState<Boolean>,
    viewModel: RegistrationViewModel,
) {
    if (openDialog.value) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        datePickerState.selectedDateMillis?.let {
                            viewModel.onBirthDateChanged(it)
                        }
                    },
                ) {
                    Text(stringResource(id = R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun OpenImagePicker(openImagePicker: MutableState<Boolean>) {
    if (openImagePicker.value) {
        val pickMedia = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                it?.let { uri ->
                    uri.hashCode()
                }
                openImagePicker.value = false
            }
        )

        SideEffect {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
}

@Composable
private fun OpenVideoPicker(openVideoPicker: MutableState<Boolean>) {
    if (openVideoPicker.value) {
        val pickMedia = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = {
                it?.let { uri ->
                    uri.hashCode()
                }
                openVideoPicker.value = false
            }
        )

        SideEffect {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly))
        }
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    RegistrationInfo(
        background = Color.White,
        state = RegistrationViewModel.ViewState(
            birthDate = null to "test date",
        ),
        openImagePicker = remember { mutableStateOf(false) },
        openVideoPicker = remember { mutableStateOf(false) },
    )
}

@Composable
fun RegistrationInfo(
    background: Color = Color.Transparent,
    viewModel: RegistrationViewModel? = null,
    state: RegistrationViewModel.ViewState,
    openImagePicker: MutableState<Boolean>,
    openVideoPicker: MutableState<Boolean>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .verticalScroll(ScrollState(0))
            .simplePadding(
                horizontal = 8.dp
            ),
    ) {
        StartTitle {
            this.align(Alignment.CenterHorizontally)
        }

        RegistrationNameBirthDate(viewModel, state)
        RegistrationLogo(openImagePicker)
        RegistrationVideo(openVideoPicker)
        RegistrationPassword(viewModel)
        RegistrationEmail(viewModel)
        RegistrationPhone(viewModel)
        RegistrationSex(viewModel, state)

        DefaultButton(
            modifier = Modifier
                .fillMaxWidth()
                .simplePadding(
                    top = 8.dp,
                ),
            onClick = {
                viewModel?.onRegistrationAction()
            }
        ) {
            Text(text = stringResource(id = R.string.sign_up_register_action))
        }
    }
}

@Composable
fun RegistrationNameBirthDate(
    viewModel: RegistrationViewModel?,
    state: RegistrationViewModel.ViewState,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var nameValue by rememberSaveable { mutableStateOf(EMPTY_STRING) }
        DefaultTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .simplePadding(end = 4.dp),
            value = nameValue,
            onValueChange = { newText ->
                nameValue = newText
                viewModel?.onNameChanged(nameValue)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = stringResource(id = R.string.sign_up_name_label),
            placeholder = stringResource(id = R.string.sign_up_name_hint),
        )

        DefaultTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1F)
                .simplePadding(start = 4.dp)
                .clickable {
                    viewModel?.onBirthDateAction()
                },
            value = state.birthDate.second,
            enabled = false,
            onValueChange = {},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Magenta,
                unfocusedBorderColor = Pink80,
                disabledBorderColor = Pink80,
                disabledTextColor = Color.Black,
            )
        )
    }
}

@Composable
fun RegistrationLogo(openImagePicker: MutableState<Boolean>) {
    // openImagePicker()
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .aspectRatio(ratio = 1f)
                .clickable {
                    openImagePicker.value = true
                },
            painter = painterResource(id = R.drawable.ic_registration_person),
            contentDescription = "Image for uploaded logo",
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .clickable {
                    openImagePicker.value = true
                },
            text = stringResource(id = R.string.sign_up_birth_logo_label),
        )
    }
}

@Composable
fun RegistrationVideo(openVideoPicker: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .aspectRatio(ratio = 1f)
                .clickable {
                    openVideoPicker.value = true
                },
            painter = painterResource(id = R.drawable.ic_registration_video),
            contentDescription = "Image for uploaded logo",
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
                .clickable {
                    openVideoPicker.value = true
                },
            text = stringResource(id = R.string.sign_up_birth_video_label),
        )
    }
}

@Composable
fun RegistrationPassword(
    viewModel: RegistrationViewModel?,
) {
    var passwordValue by rememberSaveable { mutableStateOf(EMPTY_STRING) }
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = passwordValue,
        onValueChange = { newText ->
            passwordValue = newText
            viewModel?.onPasswordChanged(passwordValue)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        label = stringResource(id = R.string.sign_up_password_label),
    )
}

@Composable
fun RegistrationEmail(
    viewModel: RegistrationViewModel?,
) {
    var emailValue by rememberSaveable { mutableStateOf(EMPTY_STRING) }
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = emailValue,
        onValueChange = { newText ->
            emailValue = newText
            viewModel?.onEmailChanged(emailValue)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = stringResource(id = R.string.sign_up_email_label),
        placeholder = stringResource(id = R.string.sign_up_email_hint),
    )
}

@Composable
fun RegistrationPhone(
    viewModel: RegistrationViewModel?,
) {
    var mobilePhoneValue by rememberSaveable { mutableStateOf(EMPTY_STRING) }
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = mobilePhoneValue,
        onValueChange = { newText ->
            mobilePhoneValue = newText
            viewModel?.onPhoneChanged(mobilePhoneValue)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        label = stringResource(id = R.string.sign_up_phone_label),
        placeholder = stringResource(id = R.string.sign_up_phone_hint),
    )
}

@Composable
fun RegistrationSex(
    viewModel: RegistrationViewModel?,
    state: RegistrationViewModel.ViewState,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .simplePadding(
                top = 8.dp,
            ),
    ) {
        var switchCheckedState by remember { mutableStateOf(false) }
        Switch(
            checked = switchCheckedState,
            onCheckedChange = {
                switchCheckedState = it
                viewModel?.onSexChanged(switchCheckedState)
            }
        )

        Text(
            modifier = Modifier
                .simplePadding(
                    start = 8.dp,
                )
                .align(Alignment.CenterVertically),
            text = stringResource(id = state.sex.titleRes),
        )
    }
}