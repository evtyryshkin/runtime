package ru.tyryshkin.core.domain.entity

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.tyryshkin.core.R

data class Distance(
    val value: Float
) {
    @Composable
    fun getDistanceWithParameterString(): String {
        val distanceString: String = "%.2f".format(value.takeIf { it < 1000 } ?: (value / 1000))
        val distanceParameter = stringResource(
            if (value >= 1000) R.string.km else R.string.m
        )
        return "$distanceString $distanceParameter"
    }
}
