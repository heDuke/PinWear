package com.wear.pin.domain.model

/**
 * Represents the authentication state of the application.
 */
sealed interface AuthState {
    /**
     * The app has a valid or newly acquired token and is authenticated.
     */
    data object Authenticated : AuthState

    /**
     * The app is currently refreshing an expired token.
     */
    data object Refreshing : AuthState

    /**
     * User is not authenticated.
     */
    data object Unauthenticated : AuthState

    /**
     * Authentication is in progress.
     */
    data object Loading : AuthState
}
