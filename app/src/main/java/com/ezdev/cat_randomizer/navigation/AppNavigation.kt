package com.ezdev.cat_randomizer.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.ezdev.cat_randomizer.R
import com.ezdev.cat_randomizer.core.presentation.CatScreen
import com.ezdev.cat_randomizer.settings.presentation.SettingsScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(
//    modifier: Modifier = Modifier,
    startDestination: Screen = Screen.Core,
    navController: NavHostController = rememberNavController()
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen: Screen = when (currentBackStackEntry?.destination?.route) {
        Screen.Core.javaClass.canonicalName -> Screen.Core
        else -> Screen.Settings
    }

    val navGraph = navController.createGraph(startDestination) {
        composable<Screen.Core> {
            CatScreen(
                onNavigateToSettings = { navController.navigate(Screen.Settings) },
                onShowDownloadMessage = {
                    scope.launch { snackBarHostState.showSnackbar(it) }
                })
        }
        composable<Screen.Settings> { SettingsScreen() }
    }



    Scaffold(
        topBar = {
            navController.previousBackStackEntry?.let {
                TopAppBar(
                    title = {
                        Text(text = currentScreen.toString())
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = "Back"
                            )
                        }
                    },
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        modifier = Modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            graph = navGraph,
            modifier = Modifier.then(
                if (currentScreen == Screen.Core) {
                    Modifier
                } else {
                    Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                }
            )
        )
    }
}