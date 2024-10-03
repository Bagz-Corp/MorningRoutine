package com.example.morningroutine.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.morningroutine.navigation.FinanceScreenRoute
import com.example.morningroutine.navigation.HomeScreenRoute
import com.example.morningroutine.navigation.TopLevelDestinations

@Composable
fun rememberMrAppState(
    navController: NavHostController = rememberNavController()
): MrAppState {
    return MrAppState(navController)
}

@Stable
class MrAppState(
    val navController: NavHostController
) {

    private val currentDestination: NavDestination?
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestinations?
        @Composable
        get() = when(currentDestination?.route) {
            HomeScreenRoute::class.java.name -> TopLevelDestinations.HOME
            FinanceScreenRoute::class.java.name -> TopLevelDestinations.FINANCE
            else -> {
                Log.i("MrAppState", "Unknown current destination: ${currentDestination?.route}")
                null
            }
        }

    val topLevelDestinations: List<TopLevelDestinations> = TopLevelDestinations.entries

    fun navigateToTopLevelDestination(topLevelDestinations: TopLevelDestinations) {
        navOptions {
            // Avoid multiple copies of the same destination when
            // selecting again the same item
            launchSingleTop = true
            // Restore state when re-selecting a previously selected item
            restoreState = true
        }.apply {
            when(topLevelDestinations) {
                TopLevelDestinations.HOME -> {
                    navController.navigate(HomeScreenRoute, this)
                }
                TopLevelDestinations.FINANCE -> {
                    navController.navigate(FinanceScreenRoute, this)
                }
                else -> Log.e("MrNavHost", "Unknown top level destination: $topLevelDestinations")
            }
        }
    }

    fun navigateBack() = navController.popBackStack()

}
