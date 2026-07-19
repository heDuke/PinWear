package com.wear.pin.data.repository

import com.wear.pin.domain.model.AuthState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FakeAuthRepositoryTest {
    @Test
    fun `initial state is Unauthenticated`() =
        runTest {
            val repository = FakeAuthRepository()
            val state = repository.getAuthState().first()

            assertEquals(AuthState.Unauthenticated, state)
        }

    @Test
    fun `login transitions state to Loading then Authenticated`() =
        runTest {
            val repository = FakeAuthRepository()

            // Launch login in a separate coroutine so we can advance time
            val job =
                launch {
                    repository.login()
                }

            // Yield to allow the launched coroutine to start
            yield()

            // At this point it should be loading
            assertEquals(AuthState.Loading, repository.getAuthState().first())

            // Advance time to simulate processing delay
            advanceTimeBy(1001)

            // At this point it should be authenticated
            assertEquals(AuthState.Authenticated, repository.getAuthState().first())

            job.cancel()
        }

    @Test
    fun `logout transitions state to Unauthenticated`() =
        runTest {
            val repository = FakeAuthRepository()

            // Simulate already logged in
            val job =
                launch {
                    repository.login()
                }
            yield()
            advanceTimeBy(1001)

            assertEquals(AuthState.Authenticated, repository.getAuthState().first())

            repository.logout()

            assertEquals(AuthState.Unauthenticated, repository.getAuthState().first())

            job.cancel()
        }
}
