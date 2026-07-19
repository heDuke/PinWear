package com.wear.pin.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.navigation3.rememberSwipeDismissableSceneStrategy
import com.wear.pin.data.repository.FakeAuthRepository
import com.wear.pin.presentation.login.LoginScreen
import com.wear.pin.presentation.login.LoginViewModel
import com.wear.pin.presentation.theme.WearAppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    // For this Sprint, instantiate FakeAuthRepository directly here.
    private val authRepository = FakeAuthRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp(authRepository)
        }
    }
}

@Serializable
sealed interface AppKey : NavKey

@Serializable
data object LoginNavScreen : AppKey

@Composable
fun WearApp(authRepository: FakeAuthRepository) {
    val backStack = rememberNavBackStack(LoginNavScreen)

    WearAppTheme {
        AppScaffold {
            val entryProvider =
                remember {
                    entryProvider<NavKey> {
                        entry<LoginNavScreen> {
                            // Use the custom factory to inject FakeAuthRepository
                            val viewModel: LoginViewModel =
                                viewModel(
                                    factory = LoginViewModel.provideFactory(authRepository)
                                )
                            LoginScreen(viewModel = viewModel)
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
