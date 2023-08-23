package com.joy.yariklab.navigation

import androidx.navigation.NavController

interface FlowCoordinator {

    fun back()

    fun goToCurrenciesList()

    fun goToCurrencyDetails(currencyCode: String)

    fun goToMusic()

    fun goToWeather()

    fun goToLogin()

    fun goToRegistration()
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

    override fun goToLogin() {
        navController.navigate(
            route = Navigation.Login.destination
        )
    }

    override fun goToRegistration() {
        navController.navigate(
            route = Navigation.Registration.destination
        )
    }
}