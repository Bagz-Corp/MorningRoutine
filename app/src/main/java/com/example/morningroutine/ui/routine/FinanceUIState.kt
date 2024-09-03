package com.example.morningroutine.ui.routine

sealed interface FinanceUIState {

    data object Loading : FinanceUIState

    data class Success(
        val stocks: List<StockInfo>
    ) : FinanceUIState

    data object Error : FinanceUIState
}