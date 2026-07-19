package com.wear.pin.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.wear.pin.domain.model.AuthState
import com.wear.pin.domain.repository.AuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
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
                authRepository.login()
            } catch (e: Exception) {
                // Since FakeAuthRepository currently does not throw, this is defensive.
                // If it threw an error, we would map it to LoginUiState.Error through a more complex state mechanism,
                // but for now the AuthState handles core states and we are not expecting exceptions from FakeAuthRepository login.
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
