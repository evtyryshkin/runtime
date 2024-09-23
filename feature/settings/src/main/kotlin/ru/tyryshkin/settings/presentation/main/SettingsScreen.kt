package ru.tyryshkin.settings.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import ru.tyryshkin.core.test.TestTag
import ru.tyryshkin.coreui.component.RCell
import ru.tyryshkin.coreui.component.RIcon
import ru.tyryshkin.coreui.component.RTopAppBar
import ru.tyryshkin.settings.R

@Composable
fun SettingsScreen(onNavigateToTrackerSettings: () -> Unit) {
    Scaffold(
        topBar = {
            RTopAppBar(stringResource(R.string.settings_title))
        }
    ) { paddingValues ->
        Content(
            modifier = Modifier.padding(paddingValues),
            onNavigateToTrackerSettings = onNavigateToTrackerSettings
        )
    }
}

@Composable
private fun Content(modifier: Modifier, onNavigateToTrackerSettings: () -> Unit) {
    Column(modifier) {
        RCell(
            title = stringResource(R.string.profile_settings),
            leadingContent = {
                RIcon(
                    icon = R.drawable.ic_profile_settings
                )
            },
            trailingContent = {
                RIcon(
                    icon = ru.tyryshkin.coreui.R.drawable.ic_arrow_right,
                    tint = MaterialTheme.colorScheme.secondary
                )
            },
            onClick = {} // TODO Next Task about settings of profile
        )
        RCell(
            modifier = Modifier.testTag(TestTag.Settings.TRACKER),
            title = stringResource(R.string.tracker_settings),
            leadingContent = {
                RIcon(
                    icon = R.drawable.ic_race_settings
                )
            },
            trailingContent = {
                RIcon(
                    icon = ru.tyryshkin.coreui.R.drawable.ic_arrow_right,
                    tint = MaterialTheme.colorScheme.secondary
                )
            },
            onClick = onNavigateToTrackerSettings
        )
    }
}
