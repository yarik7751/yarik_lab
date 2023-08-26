package com.joy.yariklab.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.joy.yariklab.features.login.LoginScreen
import com.joy.yariklab.features.registration.RegistrationScreen
import com.joy.yariklab.features.start.StartScreen
import com.joy.yariklab.features.userlist.UserListScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.buildNavigation(navController: NavController) {

    val flowCoordinator: FlowCoordinator = FlowCoordinatorImpl(navController)

    val startDestination = Navigation.Start.destination

    navigation(
        startDestination = startDestination,
        route = Navigation.Root.destination
    ) {
        composable(
            route = Navigation.Start.destination
        ) {
            StartScreen(
                viewModel = koinViewModel(),
                flowCoordinator = flowCoordinator,
            )
        }

        composable(
            route = Navigation.Login.destination
        ) {
            LoginScreen(
                viewModel = koinViewModel(),
                flowCoordinator = flowCoordinator,
            )
        }

        composable(
            route = Navigation.Registration.destination
        ) {
            RegistrationScreen(
                viewModel = koinViewModel(),
                flowCoordinator = flowCoordinator,
            )
        }

        composable(
            route = Navigation.UserList.destination
        ) {
            UserListScreen(
                viewModel = koinViewModel(),
                flowCoordinator = flowCoordinator,
            )
        }
    }
}