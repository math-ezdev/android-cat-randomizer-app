package com.ezdev.cat_randomizer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.ezdev.cat_randomizer.core.presentation.CatScreen
import com.ezdev.cat_randomizer.settings.presentation.SettingsScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    startDestination: Screen = Screen.Core,
    navController: NavHostController = rememberNavController()
) {
    val navGraph = navController.createGraph(startDestination) {
        composable<Screen.Core> { CatScreen(onNavigateToSettings = { navController.navigate(Screen.Settings) }) }
        composable<Screen.Settings> { SettingsScreen() }

    }

    NavHost(navController = navController, graph = navGraph, modifier = modifier)
}