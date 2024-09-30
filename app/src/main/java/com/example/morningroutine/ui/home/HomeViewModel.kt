package com.example.morningroutine.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morningroutine.core.data.repository.UserPreferencesRepository
import com.example.morningroutine.model.RoutineType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeViewModel"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val defaultRoutines: List<RoutineType> = listOf(RoutineType.FINANCE)

    init {
        // Add default routines to user preferences
        defaultRoutines.map { addRoutine(it.type) }
    }

    val homeUiState: StateFlow<HomeUIState> = userPreferencesRepository.userDataFlow
        .map { userData ->
            HomeUIState.Success(
                routines = userData.registeredRoutines
            )
        }
        .catch { error ->
            Log.e(TAG, "Error loading user preferences with ${error.message}")
            HomeUIState.Error
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUIState.Loading
        )

    fun addRoutine(routineType: Int) {
        viewModelScope.launch {
            userPreferencesRepository.addRoutine(routineType)
        }
    }

    fun clearPrefs() {
        viewModelScope.launch {
            userPreferencesRepository.clearStocksPrefs()
        }
    }
}