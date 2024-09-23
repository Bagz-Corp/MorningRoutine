package com.example.morningroutine.core.data.repository

import com.example.morningroutine.model.MorningRoutine
import com.example.morningroutine.model.Routine
import com.example.morningroutine.model.RoutineType
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    val userPreferencesFlow: Flow<UserData>

    suspend fun updateStockSymbols(stockSymbols: List<String>)

    suspend fun addStockSymbol(stockSymbol: String)

    suspend fun clearPrefs()

    suspend fun addRoutine(routineType: Int)
}

data class UserData(
    val registeredRoutines: List<Int>,
    val stockSymbols: List<String>
)