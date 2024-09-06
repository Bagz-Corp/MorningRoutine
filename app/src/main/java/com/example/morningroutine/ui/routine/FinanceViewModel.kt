package com.example.morningroutine.ui.routine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morningroutine.core.data.repository.StockRepository
import com.example.morningroutine.core.data.repository.StockRepositoryImpl
import com.example.morningroutine.core.data.repository.UserPreferencesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TAG = "FinanceViewModel"

class FinanceViewModel(
    val stockRepository: StockRepository = StockRepositoryImpl(),
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val financeUiState: StateFlow<FinanceUIState> =
        userPreferencesRepository.userPreferencesFlow
            .mapLatest { userPreferences ->
                FinanceUIState.Success(userPreferences.stockSymbols)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = FinanceUIState.Loading
            )

    fun addSymbol(symbol: String) {
        viewModelScope.launch {
            Log.i(TAG, "addSymbol: $symbol")
            userPreferencesRepository.addStockSymbol(symbol)
        }
    }
}