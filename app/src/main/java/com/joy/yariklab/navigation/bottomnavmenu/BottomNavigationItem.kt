package com.joy.yariklab.navigation.bottomnavmenu

import androidx.annotation.DrawableRes
import com.joy.yariklab.R
import com.joy.yariklab.navigation.Navigation

sealed class BottomNavigationItem(
    val title: String,
    @DrawableRes val icon: Int,
    val rote: String,
) {

    object Music : BottomNavigationItem(
        title = "Music",
        icon = R.drawable.ic_music_note,
        rote = Navigation.Music.destination,
    )

    object Currency : BottomNavigationItem(
        title = "Currency",
        icon = R.drawable.ic_currency_exchange,
        rote = Navigation.CurrenciesList.destination,
    )

    object Weather : BottomNavigationItem(
        title = "Weather",
        icon = R.drawable.ic_weather,
        rote = Navigation.Weather.destination,
    )
}
