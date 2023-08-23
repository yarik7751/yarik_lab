package com.joy.yariklab.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.joy.yariklab.navigation.Navigation
import com.joy.yariklab.navigation.buildNavigation
import com.joy.yariklab.toolskit.EMPTY_STRING
import com.joy.yariklab.ui.theme.YariklabTheme
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val openDialog = remember {
                mutableStateOf(
                    DialogInfo(
                        isVisible = false,
                        title = EMPTY_STRING,
                        message = EMPTY_STRING,
                    )
                )
            }
            YariklabTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Navigation.Root.destination,
                    ) {
                        buildNavigation(navController)
                    }
                }
            }

            if (openDialog.value.isVisible) {
                ShowAlertDialog(openDialog)
            }

            LaunchedEffect(key1 = Unit) {
                viewModel.singleEvents.collectLatest { event ->
                    return@collectLatest when (event) {
                        is MainViewModel.Event.ShowError -> {
                            openDialog.value = openDialog.value.copy(
                                isVisible = true,
                                title = event.title,
                                message = event.message,
                            )
                        }
                    }}
                }
            }
    }

    @Composable
    private fun ShowAlertDialog(openDialog: MutableState<DialogInfo>) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(text = openDialog.value.title)
            },
            text = {
                Text(openDialog.value.message)
            },
            confirmButton = {},
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = openDialog.value.copy(isVisible = false)
                    }) {
                    Text("Cancel")
                }
            }
        )
    }
}