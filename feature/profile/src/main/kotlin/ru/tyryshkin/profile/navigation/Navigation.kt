package ru.tyryshkin.profile.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.navigator.graph
import ru.tyryshkin.navigator.route
import ru.tyryshkin.profile.presentation.ProfileMainScreen
import ru.tyryshkin.profile.presentation.race.RaceDescriptionScreen

fun NavGraphBuilder.profileGraph(navController: NavController, errorHandler: ErrorHandler) =
    graph(ProfileGraphDestination, ProfileDestination) {
        route(ProfileDestination) {
            ProfileMainScreen(
                errorHandler = errorHandler,
                vm = hiltViewModel(),
                onNavigateToRaceDetail = { raceId ->
                    navController.navigate(RaceDescriptionDestination.createRoute(raceId))
                }
            )
        }
        route(RaceDescriptionDestination) {
            RaceDescriptionScreen(
                errorHandler = errorHandler,
                vm = hiltViewModel(),
                onNavigateToBack = { navController.popBackStack() }
            )
        }
    }
