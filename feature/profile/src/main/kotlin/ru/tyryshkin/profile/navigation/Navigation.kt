package ru.tyryshkin.profile.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.navigator.graph
import ru.tyryshkin.navigator.route
import ru.tyryshkin.profile.presentation.ProfileMainScreen
import ru.tyryshkin.tracker.navigation.FinishDestination

fun NavGraphBuilder.profileGraph(navController: NavController, errorHandler: ErrorHandler) = graph(ProfileGraphDestination, ProfileDestination) {
    route(ProfileDestination) {
        ProfileMainScreen(
            errorHandler = errorHandler,
            vm = hiltViewModel(),
            onNavigateToRaceDetail = { raceId ->
                navController.navigate(FinishDestination.createRoute(raceId)) // TODO не нужно навигироваться на экран фичи другого модуля, так было сделано для скорости
            }
        )
    }
}
