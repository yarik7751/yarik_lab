package com.joy.yariklab.features.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joy.yariklab.R
import com.joy.yariklab.features.common.logo.StartTitle
import com.joy.yariklab.features.login.LoginViewModel.Event
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.ui.theme.DefaultButton
import com.joy.yariklab.ui.theme.DefaultTextField
import com.joy.yariklab.uikit.LabProgressBar
import com.joy.yariklab.uikit.simplePadding
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    flowCoordinator: FlowCoordinator,
) {
    val state = viewModel.viewState.collectAsState()

    if (state.value.isLoading) {
        LabProgressBar()
    }

    LoginScreenInfo(
        viewModel = viewModel
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.singleEvents.collectLatest { event ->
            when (event) {
                Event.GoToUserList -> {
                    flowCoordinator.goToUserList()
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreenInfo(
        background = Color.White,
    )
}

@Composable
fun LoginScreenInfo(
    background: Color = Color.Transparent,
    viewModel: LoginViewModel? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
    ) {
        StartTitle {
            this.align(Alignment.TopCenter)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .simplePadding(
                    horizontal = 8.dp,
                )
                .align(Alignment.Center),
        ) {
            LoginActions(viewModel)

            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .simplePadding(
                        top = 8.dp,
                    ),
                onClick = {
                    viewModel?.onLoginAction()
                }
            ) {
                Text(text = stringResource(id = R.string.start_login))
            }
        }
    }
}

@Composable
fun LoginActions(
    viewModel: LoginViewModel?,
) {
    var loginValue by rememberSaveable { mutableStateOf(/*EMPTY_STRING*/"+48570234871") }
    viewModel?.onLoginChanged(loginValue)
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = loginValue,
        onValueChange = { newText ->
            loginValue = newText
            viewModel?.onLoginChanged(loginValue)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = stringResource(id = R.string.sign_in_login_label),
        placeholder = stringResource(id = R.string.sign_in_login_hint),
    )

    var passwordValue by rememberSaveable { mutableStateOf(/*EMPTY_STRING*/"qwerty123") }
    viewModel?.onPasswordChanged(passwordValue)
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth()
            .simplePadding(
                top = 8.dp,
            ),
        value = passwordValue,
        onValueChange = { newText ->
            passwordValue = newText
            viewModel?.onPasswordChanged(passwordValue)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        label = stringResource(id = R.string.sign_in_password_label),
        placeholder = stringResource(id = R.string.sign_in_password_hint),
    )
}