package com.example.morningroutine.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.morningroutine.core.data.repository.StockRepository
import com.example.morningroutine.core.data.repository.DataStoreRepository
import com.example.morningroutine.core.data.repository.UserPreferencesRepository
import com.example.morningroutine.ui.home.HomeScreenRoute
import com.example.morningroutine.ui.routine.FinanceRoute
import com.example.morningroutine.ui.routine.FinanceViewModel

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

fun NavGraphBuilder.routineScreen(
    stockRepository: StockRepository,
    dataStoreRepository: UserPreferencesRepository
) {
    composable(
        route = ROUTINE_SCREEN_ROUTE
    ) {
        FinanceRoute(
             viewModel = FinanceViewModel(
                 stockRepository = stockRepository,
                 dataStoreRepository = dataStoreRepository
             )
        )
    }
}