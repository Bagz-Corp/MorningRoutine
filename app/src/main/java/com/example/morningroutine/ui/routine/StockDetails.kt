package com.example.morningroutine.ui.routine

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.morningroutine.core.theme.GreenGray90
import com.example.morningroutine.core.ui.Chart
import com.example.morningroutine.core.ui.DateRange

private const val TAG = "StockDetails"

@Composable
fun StockDetails(
    modifier: Modifier = Modifier,
    stockInfo: StockInfo,
    viewModel: StockDetailsViewModel = hiltViewModel<StockDetailsViewModel, StockDetailsViewModel.StockDetailsViewModelFactory> {
        it.create(stockInfo.symbol)
    }
) {
    val uiState by viewModel.detailsUiState.collectAsStateWithLifecycle()

    StockDetails(
        modifier = modifier,
        stockInfo = stockInfo,
        uiState = uiState
    )
}

@Composable
fun StockDetails(
    modifier: Modifier = Modifier,
    stockInfo: StockInfo,
    uiState: DetailsState
) {
    Log.i(TAG, "Entered StockDetails screen")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GreenGray90)
            .padding(16.dp),
    ) {

        StockHeader(stockInfo = stockInfo)
        HorizontalDivider()
        StockBody(
            modifier = modifier,
            isLoading = uiState.isLoading,
            chartsData = uiState.chartsData
        )
    }
}

@Composable
fun StockHeader(
    modifier: Modifier = Modifier,
    stockInfo: StockInfo
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Text(text = stockInfo.symbol, style = MaterialTheme.typography.headlineMedium)
        Text(text = stockInfo.name, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Price: ${stockInfo.latestValue}", style = MaterialTheme.typography.bodyLarge)
        // Text(text = "Last updated date: ${stockInfo.lastUpdateDateTime}", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun StockBody(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    chartsData: List<Float>
) {
    Log.i(TAG, "Composing StockBody")
    if (isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Chart(
            values = chartsData,
            dateRange = DateRange.WEEK,
            modifier = modifier
        )
    }
}

@Preview
@Composable
private fun StockDetailsPreview() {
    StockDetails(
        modifier = Modifier,
        stockInfo = StockInfo(
            name = "Stock Name",
            latestValue = "8975.23"
        ),
        uiState = DetailsState(
            isLoading = false,
            chartsData = listOf(1f, 2f, 3f, 4f, -2f)
        )
    )
}
