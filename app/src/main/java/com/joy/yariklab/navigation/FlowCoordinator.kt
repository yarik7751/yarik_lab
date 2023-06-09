package com.joy.yariklab.navigation

import androidx.navigation.NavController

interface FlowCoordinator {

    fun back()

    fun goToCurrenciesList()

    fun goToCurrencyDetails(currencyCode: String)

    fun goToMusic()

    fun goToWeather()
}

class FlowCoordinatorImpl(
    private val navController: NavController,
) : FlowCoordinator {

    override fun back() {
        navController.popBackStack()
    }

    override fun goToCurrenciesList() {
        navController.navigate(
            route = Navigation.CurrenciesList.destination
        )
    }

    override fun goToCurrencyDetails(currencyCode: String) {
        navController.navigate(
            route = "${Navigation.CurrenciesDetails.destination}/$currencyCode",
        )
    }

    override fun goToMusic() {
        navController.navigate(
            route = Navigation.Music.destination
        )
    }

    override fun goToWeather() {
        navController.navigate(
            route = Navigation.Weather.destination
        )
    }
}