package com.example.morningroutine.ui

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.morningroutine.navigation.FinanceHome
import com.example.morningroutine.navigation.NestedFinanceNavigation
import com.example.morningroutine.navigation.Home
import com.example.morningroutine.navigation.StockRoute
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
            Home::class.java.name -> TopLevelDestinations.HOME
            NestedFinanceNavigation::class.java.name,
            FinanceHome::class.java.name,
            StockRoute::class.java.name -> TopLevelDestinations.FINANCE
            else -> {
                Log.i("MrAppState", "Unknown current destination: ${currentDestination?.route}")
                null
            }
        }

    fun navigateBack() = navController.popBackStack()

}
