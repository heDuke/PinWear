package com.wear.pin.presentation.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wear.pin.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun loadUserProfile() {
        viewModelScope.launch {
            _uiState.value = UserUiState.Loading
            userRepository.getCurrentUser().fold(
                onSuccess = { user ->
                    _uiState.value = UserUiState.Success(user)
                },
                onFailure = { error ->
                    _uiState.value = UserUiState.Error(error.message ?: "Failed to load profile")
                }
            )
        }
    }
}
