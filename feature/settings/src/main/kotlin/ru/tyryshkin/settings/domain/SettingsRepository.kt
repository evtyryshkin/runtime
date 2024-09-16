package ru.tyryshkin.settings.domain

interface SettingsRepository {
    suspend fun getCountDownBeforeRunValue(): Int?
    suspend fun setCountDownBeforeRunValue(value: Int?)
}
