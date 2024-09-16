package ru.tyryshkin.runtime.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.tyryshkin.core.test.TestTag
import ru.tyryshkin.profile.navigation.ProfileDestination
import ru.tyryshkin.runtime.R
import ru.tyryshkin.runtime.navigation.BottomNavigationTab.Profile
import ru.tyryshkin.runtime.navigation.BottomNavigationTab.Settings
import ru.tyryshkin.runtime.navigation.BottomNavigationTab.Tracker
import ru.tyryshkin.settings.navigation.SettingsDestination
import ru.tyryshkin.tracker.navigation.StartRunDestination

sealed class BottomNavigationTab(
    val route: String,
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    val testTag: String
) {
    data object Profile : BottomNavigationTab(
        route = ProfileDestination.route(),
        label = R.string.profile,
        icon = R.drawable.ic_profile,
        testTag = TestTag.BottomNavigationTab.PROFILE
    )

    data object Tracker : BottomNavigationTab(
        route = StartRunDestination.route(),
        label = R.string.tracker,
        icon = R.drawable.ic_tracker,
        testTag = TestTag.BottomNavigationTab.TRACKER
    )

    data object Settings : BottomNavigationTab(
        route = SettingsDestination.route(),
        label = R.string.settings,
        icon = R.drawable.ic_setting,
        testTag = TestTag.BottomNavigationTab.SETTINGS
    )
}

val bottomNavigationTabList = listOf(Profile, Tracker, Settings)
