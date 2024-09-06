package com.example.morningroutine.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.example.morningroutine.ui.MrAppState

@Composable
fun MrNavHost(
    appState: MrAppState,
    modifier: Modifier
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN_ROUTE,
        modifier = modifier
    ) {
        // Provide the screens there
        homeScreen(
            onRoutineClick = navController::navigateToRoutine
        )
        routineScreen(
            appState.userPreferencesRepository
        )
    }
}