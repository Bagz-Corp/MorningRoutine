package com.example.morningroutine.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.example.morningroutine.model.Destination
import com.example.morningroutine.ui.home.HomeScreen
import com.example.morningroutine.ui.routine.FinanceScreen
import com.example.morningroutine.ui.routine.StockDetails
import com.example.morningroutine.ui.routine.StockInfo
import kotlinx.serialization.Serializable

@Composable
fun MrNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier
    ) {
        composable<Home> {
            HomeScreen(
                onRoutineClick = { routineType ->
                    Log.i("NavHost", "Selecting Routine $routineType")
                    navController.navigateTo(routineType)
                }
            )
        }

        navigation<NestedFinanceNavigation>(startDestination = FinanceHome) {
            composable<FinanceHome> {
                FinanceScreen(
                    onStockClick = {
                        Log.i("NavHost", "Selecting Stock ${it.name}")
                        navController.navigate(
                            StockRoute(
                                symbol = it.symbol,
                                name = it.name,
                                value = it.latestValue
                            )
                        )
                    }
                )
            }

            composable<StockRoute> {
                val args = it.toRoute<StockRoute>()
                StockDetails(
                    stockInfo = StockInfo(
                        name = args.name,
                        latestValue = args.value,
                        symbol = args.symbol
                    )
                )
            }
        }
    }
}

// Routes
@Serializable object Home

// Routes for nested navigation
@Serializable object NestedFinanceNavigation

// Routes inside Finance nested navigation
@Serializable object FinanceHome
@Serializable data class StockRoute(
    val symbol: String,
    val name: String,
    val value: String
)

fun NavHostController.navigateTo(
    destination: Destination,
    navOptions: NavOptions? = null,
) {
    when (destination) {
        Destination.HOME -> navigate(Home, navOptions)
        Destination.FINANCE -> navigate(NestedFinanceNavigation, navOptions)
        Destination.STOCK_DETAILS -> navigate(StockRoute, navOptions)
        Destination.UNKNOWN -> {}
        else -> {}
    }
}