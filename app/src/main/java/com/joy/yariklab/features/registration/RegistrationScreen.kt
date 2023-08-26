package com.joy.yariklab.features.registration

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.toolskit.EMPTY_STRING
import com.joy.yariklab.ui.theme.DefaultButton
import com.joy.yariklab.ui.theme.DefaultTextField
import com.joy.yariklab.uikit.LabProgressBar
import com.joy.yariklab.uikit.simplePadding
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel,
    flowCoordinator: FlowCoordinator,
) {
    val state = viewModel.viewState.collectAsState()

    if (state.value.isLoading) {
        LabProgressBar()
    }

    RegistrationInfo(
        viewModel = viewModel,
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.singleEvents.collectLatest { event ->
            when (event) {
                else -> {}
            }
        }
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    RegistrationInfo(
        background = Color.White,
    )
}

@Composable
fun RegistrationInfo(
    background: Color = Color.Transparent,
    viewModel: RegistrationViewModel? = null,
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

        RegistrationNameBirthDate(viewModel)
        RegistrationLogo()
        RegistrationVideo()
        RegistrationPassword(viewModel)
        RegistrationEmail(viewModel)
        RegistrationPhone(viewModel)
        RegistrationSex(viewModel)

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
                    this.hashCode()
                },
            value = stringResource(id = R.string.sign_up_birth_date_label),
            onValueChange = {},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )
    }
}

@Composable
fun RegistrationLogo() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .aspectRatio(ratio = 1f),
            painter =painterResource(id = R.drawable.ic_registration_person),
            contentDescription = "Image for uploaded logo",
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            text = stringResource(id = R.string.sign_up_birth_logo_label),
        )
    }
}

@Composable
fun RegistrationVideo() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .aspectRatio(ratio = 1f),
            painter =painterResource(id = R.drawable.ic_registration_video),
            contentDescription = "Image for uploaded logo",
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
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
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = stringResource(id = R.string.sign_up_phone_label),
        placeholder = stringResource(id = R.string.sign_up_phone_hint),
    )
}

@Composable
fun RegistrationSex(
    viewModel: RegistrationViewModel?,
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
            text = stringResource(id = R.string.sign_up_select_your_sex),
        )
    }
}