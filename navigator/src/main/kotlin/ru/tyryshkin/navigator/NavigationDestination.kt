package ru.tyryshkin.navigator

import androidx.navigation.NamedNavArgument

interface NavigationDestination {
    val arguments: List<NamedNavArgument>
        get() = emptyList()

    fun route(): String
}
