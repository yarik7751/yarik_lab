package com.joy.yariklab.features.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.joy.yariklab.features.start.StartTitle
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.toolskit.EMPTY_STRING
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

    LoginScreenInfo()

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
fun LoginScreenPreview() {
    LoginScreenInfo(
        background = Color.White,
    )
}

@Composable
fun LoginScreenInfo(
    background: Color = Color.Transparent
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
    ) {
        StartTitle()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .simplePadding(
                    horizontal = 8.dp,
                )
                .align(Alignment.Center),
        ) {
            LoginActions()

            DefaultButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .simplePadding(
                        top = 8.dp,
                    ),
                onClick = {
                    // TODO send request for login
                }
            ) {
                Text(text = stringResource(id = R.string.start_login))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginActions() {
    var loginValue by rememberSaveable { mutableStateOf(EMPTY_STRING) }
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth(),
        value = loginValue,
        onValueChange = { newText ->
            loginValue = newText
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        label = stringResource(id = R.string.sign_in_login_label),
        placeholder = stringResource(id = R.string.sign_in_login_hint),
    )

    var passwordValue by rememberSaveable { mutableStateOf(EMPTY_STRING) }
    DefaultTextField(
        modifier = Modifier
            .fillMaxWidth()
            .simplePadding(
                top = 8.dp,
            ),
        value = passwordValue,
        onValueChange = { newText ->
            passwordValue = newText
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        label = stringResource(id = R.string.sign_in_password_label),
        placeholder = stringResource(id = R.string.sign_in_password_hint),
    )
}