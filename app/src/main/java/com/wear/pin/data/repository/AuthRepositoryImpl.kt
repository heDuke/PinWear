package com.wear.pin.data.repository

import com.wear.pin.core.auth.AuthConfig
import com.wear.pin.core.auth.OAuthStateGenerator
import com.wear.pin.core.auth.OAuthUrlBuilder
import com.wear.pin.data.mapper.toDomain
import com.wear.pin.data.remote.pinterest.OAuthRemoteDataSource
import com.wear.pin.domain.model.AuthState
import com.wear.pin.domain.model.OAuthToken
import com.wear.pin.domain.repository.AuthRepository
import com.wear.pin.domain.repository.TokenStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Real implementation of AuthRepository that interacts with Pinterest OAuth API.
 */
class AuthRepositoryImpl(
    private val remoteDataSource: OAuthRemoteDataSource,
    private val tokenStorage: TokenStorage,
    private val urlBuilder: OAuthUrlBuilder = OAuthUrlBuilder(),
    private val stateGenerator: OAuthStateGenerator = OAuthStateGenerator()
) : AuthRepository {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)
    private val refreshMutex = Mutex()
    private var pendingOAuthState: String? = null

    override fun getAuthState(): Flow<AuthState> = _authState.asStateFlow()

    override fun buildAuthorizationUrl(): String {
        val state = stateGenerator.generateState()
        pendingOAuthState = state
        return urlBuilder.buildUrl(
            clientId = AuthConfig.CLIENT_ID,
            redirectUri = AuthConfig.REDIRECT_URI,
            state = state
        )
    }

    override suspend fun handleAuthorizationResponse(
        code: String?,
        state: String?,
        error: String?
    ) {
        if (state != pendingOAuthState) {
            _authState.value = AuthState.Unauthenticated
            return
        }
        pendingOAuthState = null

        if (code != null) {
            exchangeCodeForToken(code)
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }

    override suspend fun restoreSession() {
        val token = getValidToken()
        if (token != null) {
            _authState.value = AuthState.Authenticated
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }

    override suspend fun getValidToken(): OAuthToken? =
        refreshMutex.withLock {
            val token = tokenStorage.loadToken() ?: return@withLock null

            if (token.isExpired()) {
                val refreshResult = refreshTokenInternal()
                return@withLock refreshResult.getOrNull()
            }

            return@withLock token
        }

    override suspend fun refreshToken(): Result<OAuthToken> =
        refreshMutex.withLock {
            refreshTokenInternal()
        }

    private suspend fun refreshTokenInternal(): Result<OAuthToken> {
        _authState.value = AuthState.Refreshing
        val currentToken = tokenStorage.loadToken()

        if (currentToken?.refreshToken == null) {
            logout()
            return Result.failure(IllegalStateException("No refresh token available"))
        }

        val result =
            remoteDataSource
                .refreshToken(currentToken.refreshToken)
                .map { responseDto -> responseDto.toDomain() }

        result
            .onSuccess { newToken ->
                tokenStorage.saveToken(newToken)
                _authState.value = AuthState.Authenticated
            }.onFailure {
                logout()
            }

        return result
    }

    override suspend fun exchangeCodeForToken(code: String): Result<OAuthToken> {
        val result =
            remoteDataSource
                .exchangeToken(code, AuthConfig.REDIRECT_URI)
                .map { responseDto -> responseDto.toDomain() }

        result.onSuccess { token ->
            tokenStorage.saveToken(token)
            _authState.value = AuthState.Authenticated
        }

        return result
    }

    override suspend fun login() {
        // Reserved for potential auto-login or session restoration in future sprints
    }

    override suspend fun logout() {
        tokenStorage.clearToken()
        _authState.value = AuthState.Unauthenticated
    }
}
