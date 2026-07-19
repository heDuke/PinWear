package com.wear.pin.presentation.login

import com.wear.pin.data.repository.FakeAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Unauthenticated`() =
        runTest {
            val repository = FakeAuthRepository()
            val viewModel = LoginViewModel(repository)

            val initialState = viewModel.uiState.first()
            assertEquals(LoginUiState.Unauthenticated, initialState)
        }

    @Test
    fun `onLoginClicked emits OpenBrowser event and does not authenticate`() =
        runTest {
            val repository = FakeAuthRepository()
            val viewModel = LoginViewModel(repository)

            val events = mutableListOf<LoginUiEvent>()
            val job =
                launch {
                    viewModel.uiEvent.collect { events.add(it) }
                }

            // Trigger login
            viewModel.onLoginClicked()

            // Wait for the coroutine in onLoginClicked to complete
            advanceUntilIdle()

            // Verify OpenBrowser event was emitted
            assertEquals(1, events.size)
            assertTrue(events[0] is LoginUiEvent.OpenBrowser)

            // Verify state remains Unauthenticated
            assertEquals(LoginUiState.Unauthenticated, viewModel.uiState.value)

            job.cancel()
        }
}
