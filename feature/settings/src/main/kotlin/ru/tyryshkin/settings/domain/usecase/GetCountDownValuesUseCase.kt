package ru.tyryshkin.settings.domain.usecase

import javax.inject.Inject

val defaultValues = listOf(null, 3, 5, 10, 15)

class GetCountDownValuesUseCase @Inject constructor() {
    fun invoke() = defaultValues
}
