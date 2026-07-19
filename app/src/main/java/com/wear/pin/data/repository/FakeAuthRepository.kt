package com.wear.pin.data.repository

import com.wear.pin.core.auth.AuthConfig
import com.wear.pin.core.auth.OAuthStateGenerator
import com.wear.pin.core.auth.OAuthUrlBuilder
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
class FakeAuthRepository(
    private val urlBuilder: OAuthUrlBuilder = OAuthUrlBuilder(),
    private val stateGenerator: OAuthStateGenerator = OAuthStateGenerator()
) : AuthRepository {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)

    override fun getAuthState(): Flow<AuthState> = _authState.asStateFlow()

    override fun buildAuthorizationUrl(): String =
        urlBuilder.buildUrl(
            clientId = AuthConfig.CLIENT_ID,
            redirectUri = AuthConfig.REDIRECT_URI,
            state = stateGenerator.generateState()
        )

    override suspend fun handleAuthorizationResponse(
        code: String?,
        state: String?,
        error: String?
    ) {
        // In Sprint 4B, we do NOT change the AuthState to Authenticated here.
        // Receiving an authorization code is just the first step.
        // Token Exchange will happen in Sprint 4C.

        // TODO(Sprint 4C): Validate that the received 'state' matches the one generated during buildAuthorizationUrl().

        // For now, just temporarily hold or log the result.
        // If there is an error, we might want to update the state to Unauthenticated or an Error state.
        if (error != null) {
            _authState.value = AuthState.Unauthenticated
        }
    }

    override suspend fun exchangeCodeForToken(
        code: String
    ): Result<com.wear.pin.domain.model.OAuthToken> =
        Result.success(
            com.wear.pin.domain.model.OAuthToken(
                accessToken = "fake_access_token_for_code_$code",
                tokenType = "bearer",
                expiresIn = 2592000L,
                scope = "user_accounts:read boards:read pins:read",
                refreshToken = "fake_refresh_token",
                refreshTokenExpiresIn = 5184000L
            )
        )

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
