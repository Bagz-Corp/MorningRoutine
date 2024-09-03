package com.example.morningroutine

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel: ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = TODO()
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    // data class Success(val userData: UserData) : MainActivityUiState
}