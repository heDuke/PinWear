package com.wear.pin.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wear.pin.domain.model.AuthState
import com.wear.pin.domain.repository.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    val uiState: StateFlow<LoginUiState> =
        authRepository
            .getAuthState()
            .map { authState ->
                when (authState) {
                    is AuthState.Authenticated -> LoginUiState.Authenticated
                    is AuthState.Loading -> LoginUiState.Loading
                    is AuthState.Unauthenticated -> LoginUiState.Unauthenticated
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = LoginUiState.Unauthenticated
            )

    fun onLoginClicked() {
        viewModelScope.launch {
            try {
                val url = authRepository.buildAuthorizationUrl()
                _uiEvent.send(LoginUiEvent.OpenBrowser(url))
                // Note: authRepository.login() is skipped in Sprint 4A as we delegate auth to Browser
            } catch (e: Exception) {
                // E.g. URL building failed or other unexpected errors
                _uiEvent.send(
                    LoginUiEvent.ShowError(
                        e.message ?: "Failed to build authorization URL"
                    )
                )
            }
        }
    }

    companion object {
        fun provideFactory(authRepository: AuthRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                        return LoginViewModel(authRepository) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
    }
}
