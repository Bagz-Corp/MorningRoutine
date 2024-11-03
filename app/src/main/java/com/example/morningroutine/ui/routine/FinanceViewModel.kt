package com.example.morningroutine.ui.routine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.morningroutine.core.data.repository.StockRepository
import com.example.morningroutine.core.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FinanceViewModel"

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    private val dataStoreRepository: UserPreferencesRepository,
) : ViewModel() {

    private val defaultScope = CoroutineScope(Dispatchers.Default)

    val financeUiState: StateFlow<FinanceUIState> = stockInfoFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FinanceUIState.Loading
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    // Flow retrieving first the stockSymbols stored in DataStore then call the APi to get the
    // stock info
    val stockInfoFlow : Flow<FinanceUIState>
        get() = dataStoreRepository
            .financeUserDataFlow
            .flatMapConcat { userPreferences ->
                val stockSymbols = userPreferences.stockSymbols
                if (stockSymbols.isEmpty())
                    return@flatMapConcat flowOf(FinanceUIState.Success())

                // get stock info based on the symbols
                val stockInfo = getStockInfo(stockSymbols)

                flowOf(
                    FinanceUIState.Success(
                        stockSymbols = stockSymbols,
                        stockInfo = stockInfo
                    )
                )
            }.catch { e ->
                FinanceUIState.Error(e.message)
            }

    fun addSymbol(symbol: String) {
        defaultScope.launch {
            dataStoreRepository.addStockSymbol(symbol)
        }
    }

    suspend fun refresh() {
        dataStoreRepository.updateLastUpdatedTime() // Updating last updated time fetches new data
        delay(1000) // refresh is too fast, adding delay for visibility
    }

    private suspend fun getStockInfo(symbols: List<String>): List<StockInfo> {
        try {
            return stockRepository.getLatestValues(symbols).also {
                Log.w(TAG, "Stock info received $it")
            }
        } catch (e: Error) {
            throw e
        }
    }

}