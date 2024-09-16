package ru.tyryshkin.navigator

import androidx.navigation.NavHostController

class NavHostControllerKeeper {
    var navController: NavHostController? = null
        get() = field ?: throw NavHostControllerDidNotInitializedException()
}
