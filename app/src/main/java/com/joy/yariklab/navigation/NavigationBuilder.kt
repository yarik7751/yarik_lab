package com.joy.yariklab.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.joy.yariklab.features.curencydetails.CurrencyDetailsScreen
import com.joy.yariklab.features.currencieslist.CurrenciesListScreen
import com.joy.yariklab.features.music.MusicScreen
import com.joy.yariklab.features.start.StartScreen
import com.joy.yariklab.features.weather.WeatherScreen
import com.joy.yariklab.main.IS_TEST_APP
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

private const val ARGS_CURRENCY_CODE = "code"

fun NavGraphBuilder.buildNavigation(navController: NavController) {

    val flowCoordinator: FlowCoordinator = FlowCoordinatorImpl(navController)

    val startDestination = when {
        IS_TEST_APP -> Navigation.CurrenciesList.destination
        else -> Navigation.Start.destination
    }

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
            route = Navigation.CurrenciesList.destination
        ) {
            CurrenciesListScreen(
                viewModel = koinViewModel(),
                flowCoordinator = flowCoordinator,
            )
        }

        composable(
            route = "${Navigation.CurrenciesDetails.destination}/{$ARGS_CURRENCY_CODE}",
            arguments = listOf(
                navArgument(ARGS_CURRENCY_CODE) {
                    type = NavType.StringType
                },
            ),
        ) { navBackStackEntry ->
            val currencyCode = navBackStackEntry.arguments?.getString(ARGS_CURRENCY_CODE).orEmpty()
            CurrencyDetailsScreen(
                viewModel = koinViewModel(
                    parameters = { parametersOf(currencyCode) }
                ),
                flowCoordinator = flowCoordinator,
            )
        }

        composable(
            route = Navigation.Music.destination
        ) {
            MusicScreen(
                viewModel = koinViewModel(),
                flowCoordinator = flowCoordinator,
            )
        }

        composable(
            route = Navigation.Weather.destination
        ) {
            WeatherScreen(
                viewModel = koinViewModel(),
                flowCoordinator = flowCoordinator,
            )
        }
    }
}