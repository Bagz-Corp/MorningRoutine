package com.example.morningroutine.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.morningroutine.core.ui.RoutineCard
import com.example.morningroutine.model.Destination

@Composable
fun HomeScreen(
    onRoutineClick: (Destination) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState: HomeUIState by viewModel.homeUiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeBody(
            uiState = uiState,
            onItemClick = onRoutineClick
        )
    }
}

@Composable
fun HomeBody(
    uiState: HomeUIState,
    onItemClick: (Destination) -> Unit
) {
    when (uiState) {
        is HomeUIState.Success -> {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.routines) {
                    RoutineCard(
                        destination = Destination.from(it),
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
fun HomeBodySuccessPreview() {
    HomeBody(
        uiState = HomeUIState.Success(
            routines = listOf(Destination.FINANCE.type, Destination.ROUTINE2.type)
        ),
        onItemClick = { }
    )
}