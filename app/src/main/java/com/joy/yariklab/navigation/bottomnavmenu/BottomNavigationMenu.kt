package com.joy.yariklab.navigation.bottomnavmenu

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.joy.yariklab.R
import com.joy.yariklab.navigation.FlowCoordinator
import com.joy.yariklab.navigation.FlowCoordinatorImpl

@Composable
fun BottomNavigationMenu(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Music,
        BottomNavigationItem.Currency,
        BottomNavigationItem.Weather,
    )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.teal_200),
        contentColor = Color.Black
    ) {
        val flowCoordinator: FlowCoordinator = FlowCoordinatorImpl(navController)
        val navBackStackEntry = navController.currentBackStackEntryAsState().value
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.rote,
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 9.sp,
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                onClick = {
                    when (item) {
                        BottomNavigationItem.Currency -> flowCoordinator.goToCurrenciesList()
                        BottomNavigationItem.Music -> flowCoordinator.goToMusic()
                        BottomNavigationItem.Weather -> flowCoordinator.goToWeather()
                    }
                },
            )
        }
    }
}
