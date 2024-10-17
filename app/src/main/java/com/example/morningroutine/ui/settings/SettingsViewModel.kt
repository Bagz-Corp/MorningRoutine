package com.example.morningroutine.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morningroutine.core.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
): ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val settingsUiState: StateFlow<SettingsUIState> = dataStoreRepository
        .financeUserDataFlow
        .mapLatest {
            SettingsUIState(
                isLoading = false,
                stockSymbols = it.stockSymbols,
                lastUpdated = it.lastUpdatedTime
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsUIState()
        )

    fun clearPrefs() = viewModelScope.launch {
        dataStoreRepository.clearStocksPrefs()
    }
}

data class SettingsUIState(
    val isLoading: Boolean = false,
    val stockSymbols: List<String> = emptyList(),
    val lastUpdated: Long? = null
)