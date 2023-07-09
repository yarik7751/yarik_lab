package com.joy.yariklab.main

import androidx.annotation.DrawableRes
import com.joy.yariklab.R
import com.joy.yariklab.navigation.Navigation

sealed class BottomNavigationItem(
    val title: String,
    @DrawableRes val icon: Int,
    val rote: String,
) {

    object Home : BottomNavigationItem(
        title = "Home",
        icon = R.drawable.ic_home,
        // TODO must be route
        rote = "",
    )

    object Currency : BottomNavigationItem(
        title = "Currency",
        icon = R.drawable.ic_currency_exchange,
        rote = Navigation.CurrenciesList.destination,
    )

    object Weather : BottomNavigationItem(
        title = "Weather",
        icon = R.drawable.ic_weather,
        // TODO must be route
        rote = "",
    )
}
