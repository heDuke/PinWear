package com.wear.pin.presentation.pins

import com.wear.pin.domain.model.Pin

sealed interface PinsUiState {
    data object Loading : PinsUiState

    data class Success(
        val pins: List<Pin>
    ) : PinsUiState

    data class Error(
        val message: String
    ) : PinsUiState
}
