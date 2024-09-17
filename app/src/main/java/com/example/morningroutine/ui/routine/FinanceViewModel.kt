package com.example.morningroutine.ui.routine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morningroutine.core.data.repository.StockRepository
import com.example.morningroutine.core.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FinanceViewModel"

@HiltViewModel
class FinanceViewModel @Inject constructor(
    val stockRepository: StockRepository,
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val financeUiState: StateFlow<FinanceUIState> = concatenatedFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FinanceUIState.Loading
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val concatenatedFlow : Flow<FinanceUIState>
        get() = userPreferencesRepository.userPreferencesFlow
            .flatMapConcat { userPreferences ->
                Log.i(TAG, "UserPreferences: $userPreferences")

                stockRepository.getIntradayInfo(userPreferences.stockSymbols).map {
                    Log.i(TAG, "Stock info received $it")
                    FinanceUIState.Success(
                        stockSymbols = userPreferences.stockSymbols,
                        stockInfo = it
                    )
                }

                /* val symbols = userPreferences.stockSymbols
                if (symbols.isNotEmpty()) {
                    Log.i(TAG, "Fetching stock info for: $symbols")

                    stockRepository.getIntradayInfo(symbols).map {
                        Log.i(TAG, "Stock info received $it")
                        FinanceUIState.Success(
                            stockSymbols = userPreferences.stockSymbols,
                            stockInfo = it
                        )
                    }
                } else {
                    flow {
                        FinanceUIState.Success(
                            stockSymbols = symbols,
                            stockInfo = emptyList()
                        )
                    }
                } */
            }.catch {
                FinanceUIState.Error
            }

    fun addSymbol(symbol: String) {
        viewModelScope.launch {
            Log.i(TAG, "addSymbol: $symbol")
            userPreferencesRepository.addStockSymbol(symbol)
        }
    }
}