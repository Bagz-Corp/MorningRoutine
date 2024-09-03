package com.example.morningroutine.ui.routine

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.core.theme.DarkColorScheme
import com.example.morningroutine.core.theme.MrTheme

@Composable
fun FinanceRoute(modifier: Modifier = Modifier) = FinanceScreen(modifier)

@Composable
fun FinanceScreen(
    modifier: Modifier = Modifier,
) {
    MrTheme {
        Scaffold(
            modifier = modifier,
            floatingActionButton = { SmallFab(padding = PaddingValues(16.dp)) },
        ) { padding ->
            LazyColumn(
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize(),
            ) {

            }
        }
    }
}

@Composable
private fun SmallFab(
    padding: PaddingValues
) {
    SmallFloatingActionButton(
        modifier = Modifier
            .padding(padding),
        onClick = { /*TODO*/ },
        containerColor = DarkColorScheme.secondaryContainer,
        contentColor = DarkColorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Localized description")
    }
}

private fun onFabClick() {

}

@Preview
@Composable
private fun Preview() {
    FinanceScreen()
}