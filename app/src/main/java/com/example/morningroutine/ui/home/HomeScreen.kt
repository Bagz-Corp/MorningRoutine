package com.example.morningroutine.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.morningroutine.core.ui.RoutineCard
import com.example.morningroutine.model.MorningRoutine
import com.example.morningroutine.model.Routine
import com.example.morningroutine.model.RoutineType
import com.example.morningroutine.navigation.TopLevelDestinations

// TODO : Fix design
// TODO : Add FAB to add new routines

@Composable
fun HomeScreenRoute(
    onRoutineClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
   HomeScreen(
       onRoutineClick = onRoutineClick,
       modifier = modifier,
       homeViewModel
   )
}

@Composable
fun HomeScreen(
    onRoutineClick: (String) -> Unit,
    modifier: Modifier,
    viewModel: HomeViewModel
) {
    Box(
        modifier = modifier
    ) {
        val uiState: HomeUIState by viewModel.homeUiState.collectAsStateWithLifecycle()

        // Stub Routine
        viewModel.addRoutine(RoutineType.FINANCE.type)

        HomeBody(
            uiState = uiState,
            onItemClick = onRoutineClick
        )
    }
}

@Composable
fun HomeBody(
    uiState: HomeUIState,
    onItemClick: (String) -> Unit
) {
    when (uiState) {
        is HomeUIState.Success -> {

            val items = uiState.routines.map {
                RoutineType.entries.first { routineType ->
                    routineType.type == it
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items.size) { index ->
                    RoutineCard(
                        routineType = items[index],
                        onCardClick = onItemClick,
                    )
                }
            }
        }
        else -> {
            // show error or loading
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    HomeScreenRoute(
        onRoutineClick = {},
        modifier = Modifier,
    )
}