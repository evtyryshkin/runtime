package ru.tyryshkin.tracker.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ru.tyryshkin.navigator.RouteDestination

object FinishDestination : RouteDestination {
    private const val ROUTE = "finish"
    private const val RACE_ID = "race_id"

    override val arguments = listOf(
        navArgument(RACE_ID) { type = NavType.LongType }
    )

    override fun route() = "$ROUTE/{$RACE_ID}"
    fun createRoute(raceId: Long) = "$ROUTE/$raceId"
    fun extractRaceId(savedStateHandle: SavedStateHandle): Long = savedStateHandle[RACE_ID]!!
}
