package ru.tyryshkin.runtime.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.profile.navigation.profileGraph
import ru.tyryshkin.settings.navigation.settingsGraph
import ru.tyryshkin.tracker.navigation.TrackerGraphDestination
import ru.tyryshkin.tracker.navigation.trackerGraph

@Composable
fun AppNavHost(navController: NavHostController, errorHandler: ErrorHandler, modifier: Modifier) =
    NavHost(
        navController = navController,
        startDestination = TrackerGraphDestination.route(),
        modifier = modifier
    ) {
        profileGraph(navController, errorHandler)
        trackerGraph(navController, errorHandler)
        settingsGraph(navController, errorHandler)
    }
