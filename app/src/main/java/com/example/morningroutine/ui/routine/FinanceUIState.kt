package com.example.morningroutine.ui.routine

sealed interface FinanceUIState {

    data object Loading : FinanceUIState

    data class Success(
        val stockSymbols: List<String>,
        val stockInfo: List<StockInfo>
    ) : FinanceUIState

    data object Error : FinanceUIState
}