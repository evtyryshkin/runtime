package ru.tyryshkin.navigator

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.graph(
    graphDestination: GraphDestination,
    startRouteDestination: RouteDestination,
    builder: NavGraphBuilder.() -> Unit
) = navigation(
    startDestination = startRouteDestination.route(),
    route = graphDestination.route(),
    builder = builder
)

fun NavGraphBuilder.route(
    destination: RouteDestination,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) = composable(
    route = destination.route(),
    arguments = destination.arguments,
    content = content
)

fun NavHostController.navigateUpTo(destination: NavigationDestination, inclusive: Boolean = false) {
    popBackStack(destination.route(), inclusive)
}
