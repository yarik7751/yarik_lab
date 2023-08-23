package com.joy.yariklab.navigation

sealed class Navigation(val destination: String) {

    object Root : Navigation("root")

    object Start : Navigation("Start")

    object Login : Navigation("Login")

    object Registration : Navigation("Registration")
}
