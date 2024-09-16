package ru.tyryshkin.core.informer.error

sealed class DomainException : Exception() {
    data object Development : DomainException() // TODO
    data class Default(override val message: String) : DomainException()
    data class Network(val errorCode: Int, override val message: String? = null) : DomainException()
    data class ConnectNetwork(override val message: String?) : DomainException()
    data class Unknown(val error: Throwable) : DomainException()
}
