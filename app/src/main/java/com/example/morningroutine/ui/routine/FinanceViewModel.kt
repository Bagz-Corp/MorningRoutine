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
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FinanceViewModel"

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val stockRepository: StockRepository,
    private val dataStoreRepository: UserPreferencesRepository,
) : ViewModel() {

    val financeUiState: StateFlow<FinanceUIState> = concatenatedFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = FinanceUIState.Loading
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    // Flow retrieving first the stockSymbols stored in DataStore then call the APi to get the
    // stock info
    val concatenatedFlow : Flow<FinanceUIState>
        get() = dataStoreRepository.financeUserDataFlow
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
        viewModelScope.launch {
            dataStoreRepository.addStockSymbol(symbol)
            getStockInfo(listOf(symbol))
        }
    }

    private suspend fun getStockInfo(symbols: List<String>): List<StockInfo> {
            try {
                return stockRepository.getIntradayInfo(symbols).also {
                    Log.w(TAG, "Stock info received $it")
                }
            } catch (e: Error) {
                throw e
            }
    }

}