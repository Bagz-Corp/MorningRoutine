package com.example.morningroutine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.morningroutine.core.data.repository.UserPreferencesRepository
import com.example.morningroutine.core.theme.MrTheme
import com.example.morningroutine.ui.MrApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
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