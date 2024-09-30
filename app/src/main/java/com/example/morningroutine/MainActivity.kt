package com.example.morningroutine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.morningroutine.core.data.repository.StockRepository
import com.example.morningroutine.core.data.repository.UserPreferencesRepository
import com.example.morningroutine.core.theme.MrTheme
import com.example.morningroutine.ui.MrApp
import com.example.morningroutine.ui.rememberMrAppState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    @Inject lateinit var dataStoreRepository: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            MrTheme {
                MrApp()
            }
        }
    }
}