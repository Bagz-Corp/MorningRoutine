package com.example.morningroutine.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.morningroutine.model.RoutineType
import com.example.morningroutine.ui.MrAppState
import com.example.morningroutine.ui.home.HomeScreen
import com.example.morningroutine.ui.routine.FinanceScreen
import kotlinx.serialization.Serializable

@Composable
fun MrNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeScreenRoute,
        modifier = modifier
    ) {
        composable<HomeScreenRoute> {
            HomeScreen(
                onRoutineClick = { routineType ->
                    navController.navigateTo(routineType)
                }
            )
        }

        composable<FinanceScreenRoute> {
            FinanceScreen()
        }
    }
}

@Serializable
object HomeScreenRoute

@Serializable
object FinanceScreenRoute

fun NavHostController.navigateTo(routineType: RoutineType, navOptions: NavOptions? = null) {
    when (routineType) {
        RoutineType.HOME -> navigate(HomeScreenRoute, navOptions)
        RoutineType.FINANCE -> navigate(FinanceScreenRoute, navOptions)
        RoutineType.UNKNOWN -> {}
    }
}