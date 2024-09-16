package ru.tyryshkin.core

import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import ru.tyryshkin.core.informer.error.DomainException
import ru.tyryshkin.core.presentation.State
import ru.tyryshkin.core.presentation.getValue
import ru.tyryshkin.core.presentation.updateContent

class StateTest {

    @Test
    fun update_state_isCorrect() {
        val expectedValue = 2
        val state = MutableStateFlow<State>(TestContent(1))
        val changedState = state.updateContent<TestContent> {
            it.copy(value = 2)
        }
        val actualValue = (changedState as TestContent).value
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun update_state_isError() {
        val state = MutableStateFlow<State>(State.Loading)
        assertThrows(DomainException.Development::class.java) {
            state.updateContent<TestContent> {
                it.copy(value = 2)
            }
        }
    }

    @Test
    fun get_state_value_isCorrect() {
        val expectedValue = 2
        val state = MutableStateFlow<State>(TestContent(2))
        val actualValue = state.getValue<TestContent, Int> { it.value }
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun get_state_value_isError() {
        val state = MutableStateFlow<State>(State.Error(IllegalArgumentException()))
        assertThrows(DomainException.Development::class.java) {
            state.getValue<TestContent, Int> { it.value }
        }
    }

    data class TestContent(val value: Int) : State.Content()
}
