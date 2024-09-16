package ru.tyryshkin.core.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import ru.tyryshkin.core.informer.error.DomainException
import timber.log.Timber

sealed class State {
    data object Loading : State()
    open class Content : State()
    data class Error(val exception: Exception) : State()
}

inline fun <reified T : State.Content> MutableStateFlow<State>.updateContent(block: (T) -> State.Content): State {
    this.update { block(this.value as? T ?: throwException(this.value, T::class.java.simpleName)) }
    return this.value
}

inline fun <reified T : State.Content, R> MutableStateFlow<State>.getValue(block: (T) -> R): R {
    return block(this.value as? T ?: throwException(this.value, T::class.java.name))
}

fun throwException(state: State, content: String): Nothing {
    Timber.e("Attempt to cast $state to $content")
    throw DomainException.Development
}
