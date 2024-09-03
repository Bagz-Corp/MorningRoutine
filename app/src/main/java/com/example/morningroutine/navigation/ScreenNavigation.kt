package com.example.morningroutine.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.morningroutine.ui.home.HomeScreenRoute
import com.example.morningroutine.ui.routine.FinanceRoute

const val HOME_SCREEN_ROUTE = "HomeScreenRoute"
const val ROUTINE_SCREEN_ROUTE = "RoutineScreenRoute"

fun NavController.navigateToHomeScreen(navOptions: NavOptions) = navigate(HOME_SCREEN_ROUTE, navOptions)
fun NavController.navigateToRoutine(routineName: String?, navOptions: NavOptions? = null) =
    navigate(ROUTINE_SCREEN_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(onRoutineClick: (String) -> Unit) {
    composable(
        route = HOME_SCREEN_ROUTE
    ) {
        HomeScreenRoute(onRoutineClick = onRoutineClick)
    }
}

fun NavGraphBuilder.routineScreen() {
    composable(
        route = ROUTINE_SCREEN_ROUTE
    ) {
        FinanceRoute()
    }
}