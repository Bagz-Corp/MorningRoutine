package com.example.morningroutine.ui.home

import androidx.appcompat.widget.ButtonBarLayout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.morningroutine.core.ui.RoutineCard
import com.example.morningroutine.model.RoutineType
import com.google.android.material.button.MaterialButton

@Composable
fun HomeScreen(
    onRoutineClick: (RoutineType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState: HomeUIState by viewModel.homeUiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeBody(
            uiState = uiState,
            onItemClick = onRoutineClick
        )
        Button(
            onClick = { viewModel.clearPrefs() }
        ) {
            Text("Clear Prefs")
        }
    }
}

@Composable
fun HomeBody(
    uiState: HomeUIState,
    onItemClick: (RoutineType) -> Unit
) {
    when (uiState) {
        is HomeUIState.Success -> {

            val items = uiState.routines.map {
                RoutineType.entries.first { routineType ->
                    routineType.type == it
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Adaptive(220.dp),
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
    HomeBody(
        uiState = HomeUIState.Success(
            routines = listOf(0, 1)
        ),
        onItemClick = { }
    )
}