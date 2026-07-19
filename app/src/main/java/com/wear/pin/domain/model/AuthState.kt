package com.wear.pin.domain.model

/**
 * Represents the authentication state of the application.
 */
sealed interface AuthState {
    /**
     * User is authenticated.
     */
    data object Authenticated : AuthState

    /**
     * User is not authenticated.
     */
    data object Unauthenticated : AuthState

    /**
     * Authentication is in progress.
     */
    data object Loading : AuthState
}
