package com.example.morningroutine.ui.routine

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.morningroutine.core.data.repository.StockRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = StockDetailsViewModel.StockDetailsViewModelFactory::class)
class StockDetailsViewModel @AssistedInject constructor(
    private val stockRepository: StockRepository,
    @Assisted val stockSymbol: String
) : ViewModel() {

    @AssistedFactory
    interface StockDetailsViewModelFactory {
        fun create(stockSymbol: String): StockDetailsViewModel
    }

    val detailsUiState: StateFlow<DetailsState>
        get() = stockRepository
            .getHistoricalValues(stockSymbol)
            .map {
                Log.i("StockDetailsViewModel", "Historical values retrieved: $it")
                DetailsState(
                    isLoading = false,
                    chartsData = it
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DetailsState(
                    isLoading = true,
                    chartsData = emptyList()
                )
            )
}

data class DetailsState(
    val isLoading : Boolean,
    val chartsData : List<Float>
)