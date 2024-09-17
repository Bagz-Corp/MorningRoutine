package com.example.morningroutine.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.morningroutine.core.data.repository.StockRepository
import com.example.morningroutine.core.data.repository.UserPreferencesRepository
import com.example.morningroutine.model.MorningRoutine
import com.example.morningroutine.navigation.HOME_SCREEN_ROUTE
import com.example.morningroutine.navigation.ROUTINE_SCREEN_ROUTE
import com.example.morningroutine.navigation.TopLevelDestinations

@Composable
fun rememberMrAppState(
    navController: NavHostController = rememberNavController(),
    userPreferencesRepository: UserPreferencesRepository,
    stockRepository: StockRepository
): MrAppState {
    return MrAppState(navController, userPreferencesRepository, stockRepository)
}

@Stable
class MrAppState(
    val navController: NavHostController,
    val userPreferencesRepository: UserPreferencesRepository,
    val stockRepository: StockRepository
) {

    // Contains the stored morning routines
    val morningRoutines: List<MorningRoutine> = MorningRoutine.entries

    val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestinations?
        @Composable
        get() = when(currentDestination?.route) {
            HOME_SCREEN_ROUTE -> TopLevelDestinations.HOME
            ROUTINE_SCREEN_ROUTE -> TopLevelDestinations.FINANCE
            else -> null
        }

    val topLevelDestinations: List<TopLevelDestinations> = TopLevelDestinations.entries

    fun navigateToTopLevelDestination(topLevelDestinations: TopLevelDestinations) {
        navOptions {
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }.apply {
            when(topLevelDestinations) {
                TopLevelDestinations.HOME -> {
                    navController.navigate(HOME_SCREEN_ROUTE, this)
                }
                TopLevelDestinations.FINANCE -> {
                    navController.navigate(ROUTINE_SCREEN_ROUTE, this)
                }
                else -> Log.e("MrNavHost", "Unknown top level destination: $topLevelDestinations")
            }
        }
    }
}
