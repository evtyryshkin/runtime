package ru.tyryshkin.runtime.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import ru.tyryshkin.coreui.component.RIcon
import ru.tyryshkin.coreui.component.RText
import ru.tyryshkin.coreui.theme.Typography

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    currentDestination: String,
    onTabClick: (String) -> Unit
) {
    val bottomAppBarModifier = if (currentDestination in bottomNavigationTabList.map { it.route }) {
        Modifier
    } else {
        Modifier.height(0.dp)
    }

    BottomAppBar(bottomAppBarModifier) {
        bottomNavigationTabList.forEach { navigationTab ->
            NavigationBarItem(
                modifier = Modifier.testTag(navigationTab.testTag),
                selected = currentDestination == navigationTab.route,
                label = {
                    RText(
                        text = stringResource(navigationTab.label),
                        color = MaterialTheme.colorScheme.primary,
                        style = Typography.labelSmall
                    )
                },
                icon = { RIcon(navigationTab.icon) },
                onClick = {
                    onTabClick(navigationTab.route)
                    navController.navigate(navigationTab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
