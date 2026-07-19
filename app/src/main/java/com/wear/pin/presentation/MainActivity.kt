package com.wear.pin.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.navigation3.rememberSwipeDismissableSceneStrategy
import com.wear.pin.PinWearApplication
import com.wear.pin.core.auth.OAuthCallbackHandler
import com.wear.pin.di.AppContainer
import com.wear.pin.domain.model.AuthState
import com.wear.pin.presentation.boards.BoardsScreen
import com.wear.pin.presentation.boards.BoardsViewModel
import com.wear.pin.presentation.login.LoginScreen
import com.wear.pin.presentation.login.LoginViewModel
import com.wear.pin.presentation.pins.PinsScreen
import com.wear.pin.presentation.pins.PinsViewModel
import com.wear.pin.presentation.theme.WearAppTheme
import com.wear.pin.presentation.user.ProfileScreen
import com.wear.pin.presentation.user.UserViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (application as PinWearApplication).appContainer

        lifecycleScope.launch {
            appContainer.authRepository.restoreSession()
        }

        setContent {
            WearApp(appContainer)
        }
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        val result = OAuthCallbackHandler.parseUri(intent?.data)
        if (result != null) {
            lifecycleScope.launch {
                appContainer.authRepository.handleAuthorizationResponse(
                    result.code,
                    result.state,
                    result.error
                )
            }
        }
    }
}

@Serializable
sealed interface AppKey : NavKey

@Serializable
data object LoginNavScreen : AppKey

@Serializable
data object BoardsNavScreen : AppKey

@Serializable
data class PinsNavScreen(
    val boardId: String
) : AppKey

@Serializable
data object ProfileNavScreen : AppKey

@Composable
fun WearApp(appContainer: AppContainer) {
    val backStack = rememberNavBackStack(LoginNavScreen)
    val authState by appContainer.authRepository.getAuthState().collectAsStateWithLifecycle(
        initialValue = AuthState.Loading
    )

    LaunchedEffect(authState) {
        if (authState is AuthState.Authenticated && backStack.last() == LoginNavScreen) {
            backStack.add(BoardsNavScreen)
        } else if (authState is AuthState.Unauthenticated && backStack.last() != LoginNavScreen) {
            // Need to pop everything if we logout
            while (backStack.size > 1) {
                backStack.removeLast()
            }
        }
    }

    WearAppTheme {
        AppScaffold {
            val entryProvider =
                remember(appContainer) {
                    entryProvider<NavKey> {
                        entry<LoginNavScreen> {
                            val viewModel: LoginViewModel =
                                viewModel(
                                    factory =
                                        LoginViewModel.provideFactory(
                                            appContainer.authRepository
                                        )
                                )
                            LoginScreen(viewModel = viewModel)
                        }
                        entry<BoardsNavScreen> {
                            val viewModel: BoardsViewModel =
                                viewModel(
                                    factory =
                                        BoardsViewModel.provideFactory(
                                            appContainer.boardRepository
                                        )
                                )
                            BoardsScreen(viewModel = viewModel, onBoardClick = { boardId ->
                                backStack.add(PinsNavScreen(boardId))
                            }, onProfileClick = {
                                backStack.add(ProfileNavScreen)
                            })
                        }
                        entry<PinsNavScreen> { navKey ->
                            val viewModel: PinsViewModel =
                                viewModel(
                                    factory =
                                        PinsViewModel.provideFactory(
                                            appContainer.pinRepository
                                        )
                                )
                            PinsScreen(viewModel = viewModel, boardId = navKey.boardId)
                        }
                        entry<ProfileNavScreen> {
                            val viewModel: UserViewModel =
                                viewModel(
                                    factory =
                                        UserViewModel.provideFactory(
                                            appContainer.userRepository
                                        )
                                )
                            ProfileScreen(viewModel = viewModel)
                        }
                    }
                }

            val swipeDismissableSceneStrategy = rememberSwipeDismissableSceneStrategy<NavKey>()

            NavDisplay(
                backStack = backStack,
                entryProvider = entryProvider,
                sceneStrategies = listOf(swipeDismissableSceneStrategy)
            )
        }
    }
}
