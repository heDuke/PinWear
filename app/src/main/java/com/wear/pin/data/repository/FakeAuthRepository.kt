package com.wear.pin.data.repository

import com.wear.pin.domain.model.AuthState
import com.wear.pin.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * A fake implementation of AuthRepository for UI testing and state management
 * development without hitting the real Pinterest API.
 */
class FakeAuthRepository : AuthRepository {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)

    override fun getAuthState(): Flow<AuthState> = _authState.asStateFlow()

    override suspend fun login() {
        // Simulate network/processing delay
        _authState.value = AuthState.Loading
        delay(1000)
        _authState.value = AuthState.Authenticated
    }

    override suspend fun logout() {
        _authState.value = AuthState.Unauthenticated
    }
}
