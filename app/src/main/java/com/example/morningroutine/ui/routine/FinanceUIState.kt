package com.example.morningroutine.ui.routine

sealed interface FinanceUIState {

    data object Loading : FinanceUIState

    data class Success(
        val stockSymbols: List<String>
    ) : FinanceUIState

    data object Error : FinanceUIState
}