package ru.tyryshkin.settings.domain.usecase

import ru.tyryshkin.settings.domain.SettingsRepository
import javax.inject.Inject

class GetCountDownBeforeRunValueUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(): Int? {
        return repository.getCountDownBeforeRunValue()
    }
}
