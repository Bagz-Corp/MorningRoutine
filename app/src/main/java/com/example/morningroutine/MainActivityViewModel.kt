package com.example.morningroutine

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel

class MainActivityViewModel(

): ViewModel() {

    companion object {
        private const val USER_PREFERENCES_NAME = "user_preferences"
    }

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    // data class Success(val userData: UserData) : MainActivityUiState
}