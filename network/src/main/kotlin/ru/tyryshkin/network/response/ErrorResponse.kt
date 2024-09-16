package ru.tyryshkin.network.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String
)
