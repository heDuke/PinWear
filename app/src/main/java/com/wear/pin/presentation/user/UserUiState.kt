package com.wear.pin.presentation.user

import com.wear.pin.domain.model.UserAccount

sealed interface UserUiState {
    data object Loading : UserUiState

    data class Success(
        val user: UserAccount
    ) : UserUiState

    data class Error(
        val message: String
    ) : UserUiState
}
