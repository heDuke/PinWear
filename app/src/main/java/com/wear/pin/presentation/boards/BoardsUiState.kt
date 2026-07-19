package com.wear.pin.presentation.boards

import com.wear.pin.domain.model.Board

sealed interface BoardsUiState {
    data object Loading : BoardsUiState

    data class Success(
        val boards: List<Board>
    ) : BoardsUiState

    data class Error(
        val message: String
    ) : BoardsUiState
}
