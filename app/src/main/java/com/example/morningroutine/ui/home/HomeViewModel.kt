package com.example.morningroutine.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morningroutine.core.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val homeUiState: StateFlow<HomeUIState> = userPreferencesRepository.userPreferencesFlow
        .map { userData ->
            HomeUIState.Success(
                routines = userData.registeredRoutines
            )
        }
        .catch {
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

}