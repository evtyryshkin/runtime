package ru.tyryshkin.runtime.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.tyryshkin.core.informer.error.ErrorHandler
import ru.tyryshkin.core.informer.snackbar.SnackbarController
import ru.tyryshkin.navigator.NavHostControllerKeeper
import ru.tyryshkin.runtime.informer.error.ErrorHandlerImpl
import ru.tyryshkin.runtime.informer.snackbar.SnackbarControllerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    @Singleton
    fun provideErrorHandler(impl: ErrorHandlerImpl): ErrorHandler

    @Binds
    @Singleton
    fun provideSnackbarController(impl: SnackbarControllerImpl): SnackbarController

    companion object {
        @Provides
        @Singleton
        fun provideNavHostControllerKeeper(): NavHostControllerKeeper = NavHostControllerKeeper()
    }
}
