package ru.tyryshkin.runtime.presentation.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.core.informer.snackbar.SnackbarController
import ru.tyryshkin.runtime.informer.snackbar.AppSnackbarHost
import ru.tyryshkin.runtime.navigation.AppNavHost
import ru.tyryshkin.runtime.navigation.BottomNavigationBar
import ru.tyryshkin.runtime.navigation.BottomNavigationTab.Tracker
import ru.tyryshkin.runtime.navigation.bottomNavigationTabList

@Composable
fun MainScreen(navController: NavHostController, errorHandler: ErrorHandler, snackbarController: SnackbarController) {
    val startDestination = Tracker.route
    var currentDestination by remember { mutableStateOf(startDestination) }

    var bottomNavigationBarVisible by rememberSaveable { (mutableStateOf(true)) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    navBackStackEntry?.destination?.route?.let { destination ->
        bottomNavigationBarVisible = destination in bottomNavigationTabList.map { it.route }
    }

    Scaffold(
        snackbarHost = { AppSnackbarHost(snackbarController) },
        bottomBar = {
            if (bottomNavigationBarVisible) {
                BottomNavigationBar(
                    navController = navController,
                    currentDestination = currentDestination,
                    onTabClick = { newDestination -> currentDestination = newDestination }
                )
            }
        }
    ) { innerPadding ->
        AppNavHost(navController, errorHandler, Modifier.padding(innerPadding))

        BackHandler {
            if (currentDestination != startDestination) currentDestination = startDestination
            navController.navigateUp()
        }
    }
}
