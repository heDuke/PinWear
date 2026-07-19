package com.wear.pin.presentation.login

import com.wear.pin.data.repository.FakeAuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
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
    fun `onLoginClicked transitions state to Loading then Authenticated`() =
        runTest {
            val repository = FakeAuthRepository()
            val viewModel = LoginViewModel(repository)

            // Launch a coroutine to collect uiState to keep the StateFlow active
            val job =
                launch {
                    viewModel.uiState.collect {}
                }

            // Trigger login
            viewModel.onLoginClicked()

            // Wait a little bit for the loading state to be emitted
            advanceTimeBy(100)
            assertEquals(LoginUiState.Loading, viewModel.uiState.value)

            // Advance time by the FakeAuthRepository delay (2000ms)
            advanceTimeBy(2000)
            assertEquals(LoginUiState.Authenticated, viewModel.uiState.value)

            job.cancel()
        }
}
