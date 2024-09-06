package com.example.morningroutine

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.morningroutine.core.data.repository.UserPreferencesRepository
import com.example.morningroutine.ui.MrApp
import com.example.morningroutine.ui.MrAppState
import com.example.morningroutine.ui.rememberMrAppState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    companion object {
        private const val USER_PREFERENCES_NAME = "user_preferences"
    }

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

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
        setContent {
            MrApp(
                appState = rememberMrAppState(
                    userPreferencesRepository = UserPreferencesRepository(
                        userPreferencesStore = dataStore
                    )
                )
            )
        }
    }
}