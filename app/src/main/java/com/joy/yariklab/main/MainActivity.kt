package com.joy.yariklab.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.joy.yariklab.navigation.Navigation
import com.joy.yariklab.navigation.bottomnavmenu.BottomNavigationMenu
import com.joy.yariklab.navigation.buildNavigation
import com.joy.yariklab.ui.theme.YariklabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YariklabTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()

                    Scaffold(
                        bottomBar = { BottomNavigationMenu(navController) }
                    ) {
                        it.hashCode()
                        NavHost(
                            navController = navController,
                            startDestination = Navigation.Root.destination,
                        ) {
                            buildNavigation(navController)
                        }
                    }
                }
            }
        }
    }
}