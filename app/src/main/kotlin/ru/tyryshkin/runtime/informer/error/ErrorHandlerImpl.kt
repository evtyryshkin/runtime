package ru.tyryshkin.runtime.informer.error

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import ru.tyryshkin.core.informer.error.DomainException
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.coreui.R
import ru.tyryshkin.coreui.component.RErrorPlaceholder
import ru.tyryshkin.navigator.NavHostControllerKeeper
import ru.tyryshkin.navigator.navigateUpTo
import ru.tyryshkin.tracker.navigation.StartRunDestination
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor(private val navHostControllerKeeper: NavHostControllerKeeper) : ErrorHandler {

    @Composable
    override fun OnError(exception: Exception, onRetryClick: (() -> Unit)?) {
        when (exception) {
            is DomainException.Default -> DefaultError(exception, onRetryClick)
            is DomainException.Unknown -> UnknownError(exception.error, onRetryClick)
            is DomainException.Development -> DevelopmentError()
            is DomainException.Network -> NetworkError(exception, onRetryClick)
            is DomainException.ConnectNetwork -> ConnectNetworkError(exception.message, onRetryClick)
            else -> UnknownError(exception, onRetryClick)
        }
    }

    @Composable
    private fun DefaultError(exception: Exception, onRetryClick: (() -> Unit)?) {
        Timber.e(exception)
        RErrorPlaceholder(
            painter = painterResource(R.drawable.error_common),
            subtitle = exception.message ?: stringResource(R.string.something_went_wrong),
            onActionClick = onRetryClick
        )
    }

    @Composable
    private fun UnknownError(throwable: Throwable, onRetryClick: (() -> Unit)?) {
        Timber.e(throwable)
        RErrorPlaceholder(
            painter = painterResource(R.drawable.error_common),
            subtitle = stringResource(R.string.something_went_wrong),
            onActionClick = onRetryClick
        )
    }

    @Composable
    private fun DevelopmentError() {
        RErrorPlaceholder(
            painter = painterResource(R.drawable.error_development),
            subtitle = stringResource(R.string.development_exception),
            onActionLabel = stringResource(R.string.development_exception_action_button_back_to_main),
            onActionClick = { navHostControllerKeeper.navController?.navigateUpTo(StartRunDestination) }
        )
    }

    @Composable
    private fun NetworkError(exception: DomainException.Network, onRetryClick: (() -> Unit)?) {
        RErrorPlaceholder(
            painter = painterResource(R.drawable.error_network),
            title = stringResource(R.string.network_exception_code, exception.errorCode),
            subtitle = exception.message,
            onActionLabel = stringResource(R.string.network_exception_action_button_try_to_reload),
            onActionClick = onRetryClick
        )
    }

    @Composable
    private fun ConnectNetworkError(message: String?, onRetryClick: (() -> Unit)?) {
        RErrorPlaceholder(
            painter = painterResource(R.drawable.error_network),
            subtitle = message ?: stringResource(R.string.something_went_wrong),
            onActionLabel = stringResource(R.string.network_exception_action_button_try_to_reload),
            onActionClick = onRetryClick
        )
    }
}
