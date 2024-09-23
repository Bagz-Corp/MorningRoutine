package com.example.morningroutine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.morningroutine.core.data.repository.StockRepository
import com.example.morningroutine.core.data.repository.UserPreferencesRepository
import com.example.morningroutine.ui.MrApp
import com.example.morningroutine.ui.rememberMrAppState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @Inject lateinit var dataStoreRepository: UserPreferencesRepository

    @Inject lateinit var stockRepository: StockRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO add a splash Screen
        // val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        /* var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.Loading)
        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState
                    .onEach { uiState = it }
                    .collect()
            }
        } */

        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations, and go edge-to-edge
        // This also sets up the initial system bar style based on the platform theme
        // enableEdgeToEdge()


        // To be removed
        lifecycleScope.launch {
            dataStoreRepository.clearPrefs()
        }

        setContent {
            MrApp(
                appState = rememberMrAppState(
                    dataStoreRepository = dataStoreRepository,
                    stockRepository = stockRepository
                )
            )
        }
    }
}