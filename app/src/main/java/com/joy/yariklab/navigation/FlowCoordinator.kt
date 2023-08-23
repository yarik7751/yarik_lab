package com.joy.yariklab.navigation

import androidx.navigation.NavController

interface FlowCoordinator {

    fun back()

    fun goToLogin()

    fun goToRegistration()
}

class FlowCoordinatorImpl(
    private val navController: NavController,
) : FlowCoordinator {

    override fun back() {
        navController.popBackStack()
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