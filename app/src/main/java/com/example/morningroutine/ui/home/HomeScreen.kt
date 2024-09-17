package com.example.morningroutine.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.model.MorningRoutine

// TODO : Fix design
// TODO : Add FAB to add new routines

@Composable
fun HomeScreenRoute(
    onRoutineClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
   HomeScreen(
       onRoutineClick = onRoutineClick,
       modifier = modifier
   )
}

@Composable
fun HomeScreen(
    onRoutineClick: (String) -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
    ) {
        MorningRoutineListView(
            items = MorningRoutine.entries,
            onItemClick = onRoutineClick
        )
    }
}

@Composable
fun MorningRoutineListView(items: List<MorningRoutine>, onItemClick: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items.size) { index ->
            val item = items[index]
            var isClicked by remember { mutableStateOf(false) }
            val scale = animateFloatAsState(
                targetValue = if (isClicked) 1.1f else 1f,
                label = ""
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .scale(scale.value)
                    .clickable {
                        isClicked = !isClicked
                        onItemClick(item.title)
                    },
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = item.title,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    HomeScreen(onRoutineClick = {}, modifier = Modifier)
}