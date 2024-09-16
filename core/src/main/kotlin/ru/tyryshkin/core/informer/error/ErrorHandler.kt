package ru.tyryshkin.core.informer.error

import androidx.compose.runtime.Composable
import java.lang.Exception

interface ErrorHandler {

    @Composable
    fun OnError(exception: Exception, onRetryClick: (() -> Unit)?)
}
