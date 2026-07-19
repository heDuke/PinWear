package com.wear.pin.presentation.login

/**
 * Represents the UI state for the Login screen.
 */
sealed interface LoginUiState {
    /**
     * Initial state where the user is not authenticated and the login button is shown.
     */
    data object Unauthenticated : LoginUiState

    /**
     * State where a login operation is in progress (e.g., waiting for FakeAuthRepository delay).
     */
    data object Loading : LoginUiState

    /**
     * State where the user has successfully authenticated.
     */
    data object Authenticated : LoginUiState

    /**
     * State where the authentication has failed.
     * @param message A description of the error.
     */
    data class Error(
        val message: String
    ) : LoginUiState
}
