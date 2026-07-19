package com.wear.pin.presentation.boards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wear.pin.domain.repository.BoardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BoardsViewModel(
    private val boardRepository: BoardRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<BoardsUiState>(BoardsUiState.Loading)
    val uiState: StateFlow<BoardsUiState> = _uiState.asStateFlow()

    private var hasLoaded = false

    fun loadBoards() {
        if (hasLoaded) return

        viewModelScope.launch {
            _uiState.value = BoardsUiState.Loading
            boardRepository.getBoards(bookmark = null, pageSize = 25).fold(
                onSuccess = { boardPage ->
                    hasLoaded = true
                    _uiState.value = BoardsUiState.Success(boardPage.items)
                },
                onFailure = { error ->
                    _uiState.value = BoardsUiState.Error(error.message ?: "Failed to load boards")
                }
            )
        }
    }
}
