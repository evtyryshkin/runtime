package ru.tyryshkin.runtime.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.core.informer.snackbar.SnackbarController
import ru.tyryshkin.coreui.theme.RTheme
import ru.tyryshkin.navigator.NavHostControllerKeeper
import ru.tyryshkin.runtime.presentation.main.MainScreen
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navHostControllerKeeper: NavHostControllerKeeper

    @Inject
    lateinit var errorHandler: ErrorHandler

    @Inject
    lateinit var snackbarController: SnackbarController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navHostController = rememberNavController()
            navHostControllerKeeper.navController = navHostController

            RTheme {
                MainScreen(navHostController, errorHandler, snackbarController)
            }
        }
    }
}
