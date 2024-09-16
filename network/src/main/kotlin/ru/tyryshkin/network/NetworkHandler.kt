package ru.tyryshkin.network

import kotlinx.serialization.json.Json
import retrofit2.HttpException
import retrofit2.Response
import ru.tyryshkin.core.informer.error.DomainException
import ru.tyryshkin.network.response.ErrorResponse
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class NetworkHandler @Inject constructor() {
    suspend fun <T : Any> call(request: suspend () -> Response<T>): T {
        return runCatching { request() }
            .map { response ->
                if (response.isSuccessful) {
                    response.body() ?: throw NoBodyContentException()
                } else {
                    response.errorBody()?.let { errorBody ->
                        val json = Json { ignoreUnknownKeys = true }
                        val error = runCatching {
                            json.decodeFromString<ErrorResponse>(errorBody.string())
                        }.getOrDefault(null)
                        throw DomainException.Network(response.code(), error?.message)
                    } ?: throw DomainException.Network(response.code())
                }
            }
            .getOrElse { exception ->
                throw when (exception) {
                    is UnknownHostException, is HttpException, is ConnectException, is InterruptedIOException ->
                        DomainException.ConnectNetwork(exception.message)

                    else -> DomainException.Unknown(exception)
                }
            }
    }
}

class NoBodyContentException : Exception()
