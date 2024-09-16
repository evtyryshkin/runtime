package ru.tyryshkin.settings.domain.usecase

import ru.tyryshkin.settings.domain.SettingsRepository
import javax.inject.Inject

class SetCountDownBeforeRunValueUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(value: Int?) {
        repository.setCountDownBeforeRunValue(value)
    }
}
