package com.joy.yariklab.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.joy.yariklab.features.curencydetails.CurrencyDetailsScreen
import com.joy.yariklab.features.currencieslist.CurrenciesList
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.buildNavigation(navController: NavController) {

    val flowCoordinator: FlowCoordinator = FlowCoordinatorImpl(navController)

    navigation(
        startDestination = Navigation.CurrenciesList.destination,
        route = Navigation.Root.destination
    ) {
        composable(
            route = Navigation.CurrenciesList.destination
        ) {
            CurrenciesList(
                viewModel = koinViewModel(),
                flowCoordinator = flowCoordinator,
            )
        }
        composable(
            route = "${Navigation.CurrenciesDetails.destination}/{code}",
            arguments = listOf(
                navArgument("code") {
                    type = NavType.StringType
                },
            ),
        ) {
            CurrencyDetailsScreen(
                viewModel = koinViewModel(),
                flowCoordinator = flowCoordinator,
            )
        }
    }
}