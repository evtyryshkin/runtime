package ru.tyryshkin.tracker.domain.repository

import kotlinx.coroutines.flow.Flow

interface PermissionRepository {
    val snackbarShown: Flow<Boolean>
    suspend fun snackbarShown()
}
