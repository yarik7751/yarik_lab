package com.joy.yariklab.navigation

sealed class Navigation(val destination: String) {

    object Root : Navigation("root")

    object CurrenciesList : Navigation("CurrenciesList")

    object CurrenciesDetails : Navigation("CurrenciesDetails")

    object Music : Navigation("Music")

    object Weather : Navigation("Weather")

    object Start : Navigation("Start")

    object Login : Navigation("Login")
}
