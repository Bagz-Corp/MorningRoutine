package com.example.morningroutine.ui.routine

// TODO review ui state as data class with wrapper class for specific data
sealed interface FinanceUIState {

    data object Loading : FinanceUIState

    data class Success(
        val stockSymbols: List<String> = emptyList(),
        val stockInfo: List<StockInfo> = emptyList()
    ) : FinanceUIState

    data class Error(val errorMessage: String?) : FinanceUIState
}