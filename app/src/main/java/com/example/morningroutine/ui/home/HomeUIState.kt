package com.example.morningroutine.ui.home

sealed interface HomeUIState {

    data object Loading : HomeUIState

    data class Success(
        val routines: List<Int>
    ) : HomeUIState

    data object Error : HomeUIState
}