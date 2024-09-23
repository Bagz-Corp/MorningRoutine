@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.morningroutine.ui.routine

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.morningroutine.core.theme.DarkColorScheme
import com.example.morningroutine.core.theme.MrTheme
import com.example.morningroutine.core.ui.StockCard

private const val TAG = "FinanceScreen"

@Composable
fun FinanceRoute(
    modifier: Modifier = Modifier,
    viewModel: FinanceViewModel = hiltViewModel()
) {
    FinanceScreen(
        modifier = modifier,
        viewModel = viewModel,
        onConfirmAdd = viewModel::addSymbol
    )
}

@Composable
fun FinanceScreen(
    modifier: Modifier = Modifier,
    viewModel: FinanceViewModel,
    onConfirmAdd: (String) -> Unit = {}
) {
    MrTheme {
        Scaffold(
            modifier = modifier,
            floatingActionButton = {
                SmallFAB(
                    onConfirmAdd = onConfirmAdd
                )
            },
        ) { padding ->
            val uiState: FinanceUIState by viewModel.financeUiState.collectAsStateWithLifecycle()
            StockList(
                modifier = modifier,
                padding = padding,
                uiState = uiState
            )
        }
    }
}

@Composable
private fun StockList(
    modifier: Modifier,
    padding: PaddingValues,
    uiState: FinanceUIState
) {
    when (uiState) {
        FinanceUIState.Loading -> {
            Log.i(TAG, "Loading")
        }
        FinanceUIState.Error -> {
            Log.e(TAG, "Error")
        }
        is FinanceUIState.Success -> {
            Log.i(TAG, "Success")
            val stocks = uiState.stockSymbols

            LazyColumn(
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize(),
            ) {
                items(stocks.size) { index ->
                    StockCard(
                        modifier = modifier,
                        cardTitle = stocks[index]
                    )
                }
            }
        }
    }
}

@Composable
private fun SmallFAB(
    padding: PaddingValues = PaddingValues(16.dp),
    onConfirmAdd: (String) -> Unit
) {
    val openDialog = remember { mutableStateOf(false) }

    SmallFloatingActionButton(
        modifier = Modifier.padding(padding),
        onClick = { openDialog.value = true },
        containerColor = DarkColorScheme.secondaryContainer,
        contentColor = DarkColorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Localized description")
    }

    if (openDialog.value) {
        BasicAlertDialog(
            onDismissRequest = { openDialog.value = false },
            modifier = Modifier,
            properties = DialogProperties()
        ) {
           DialogContent(
               padding = padding,
               onConfirmClick = { textInput ->
                   Log.i(TAG, "onConfirmClick, adding: $textInput")
                   onConfirmAdd(textInput)
                   openDialog.value = false
               }
           )
        }
    }
}

@Composable
private fun DialogContent(
    padding: PaddingValues,
    onConfirmClick: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .wrapContentSize()
            .padding(padding),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = AlertDialogDefaults.TonalElevation
    ) {
        var symbol by rememberSaveable { mutableStateOf("") }

        Column {
            Text(text = "Type the symbol of the stock to add to the list")
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = symbol,
                onValueChange = {
                    symbol = it
                },
                label = { Text("Stock Symbol") }
            )
            TextButton(
                onClick = { onConfirmClick(symbol) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Confirm")
            }
        }
    }
}

@Preview
@Composable
private fun DialogContentPreview() {
    DialogContent(
        padding = PaddingValues(16.dp),
        onConfirmClick = {}
    )
}