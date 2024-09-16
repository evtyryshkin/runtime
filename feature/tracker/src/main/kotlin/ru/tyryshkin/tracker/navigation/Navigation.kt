package ru.tyryshkin.tracker.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.navigator.graph
import ru.tyryshkin.navigator.navigateUpTo
import ru.tyryshkin.navigator.route
import ru.tyryshkin.tracker.presentation.finish.FinishScreen
import ru.tyryshkin.tracker.presentation.race.RaceScreen
import ru.tyryshkin.tracker.presentation.start.StartRunScreen

fun NavGraphBuilder.trackerGraph(
    navController: NavHostController,
    errorHandler: ErrorHandler
) = graph(TrackerGraphDestination, StartRunDestination) {
    route(StartRunDestination) {
        StartRunScreen(
            vm = hiltViewModel(),
            errorHandler = errorHandler,
            onNavigateToRace = {
                navController.navigate(RaceDestination.route())
            }
        )
    }
    route(RaceDestination) {
        RaceScreen(
            errorHandler = errorHandler,
            vm = hiltViewModel(),
            onNavigateToFinish = { raceId ->
                navController.navigate(FinishDestination.createRoute(raceId))
            }
        )
    }
    route(FinishDestination) {
        FinishScreen(
            errorHandler = errorHandler,
            vm = hiltViewModel(),
            onNavigateToMain = {
                navController.navigateUpTo(StartRunDestination)
            }
        )
    }
}
