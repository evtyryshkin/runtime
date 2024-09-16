package ru.tyryshkin.settings.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.navigator.graph
import ru.tyryshkin.navigator.route
import ru.tyryshkin.settings.presentation.main.SettingsScreen
import ru.tyryshkin.settings.presentation.tracker.TrackerSettingsScreen

fun NavGraphBuilder.settingsGraph(
    navController: NavHostController,
    errorHandler: ErrorHandler
) = graph(SettingsGraphDestination, SettingsDestination) {
    route(SettingsDestination) {
        SettingsScreen(
            onNavigateToTrackerSettings = {
                navController.navigate(TrackerSettingsDestination.route())
            }
        )
    }
    route(TrackerSettingsDestination) {
        TrackerSettingsScreen(
            errorHandler = errorHandler,
            vm = hiltViewModel(),
            onNavigateToBack = { navController.popBackStack() }
        )
    }
}
