package com.wear.pin.presentation.pins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wear.pin.domain.repository.PinRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PinsViewModel(
    private val pinRepository: PinRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<PinsUiState>(PinsUiState.Loading)
    val uiState: StateFlow<PinsUiState> = _uiState.asStateFlow()

    private var hasLoaded = false

    fun loadPins(boardId: String) {
        if (hasLoaded) return

        viewModelScope.launch {
            _uiState.value = PinsUiState.Loading
            pinRepository.getBoardPins(boardId, bookmark = null, pageSize = 25).fold(
                onSuccess = { pinPage ->
                    hasLoaded = true
                    _uiState.value = PinsUiState.Success(pinPage.items)
                },
                onFailure = { error ->
                    _uiState.value = PinsUiState.Error(error.message ?: "Failed to load pins")
                }
            )
        }
    }

    companion object {
        fun provideFactory(pinRepository: PinRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(PinsViewModel::class.java)) {
                        return PinsViewModel(pinRepository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}
