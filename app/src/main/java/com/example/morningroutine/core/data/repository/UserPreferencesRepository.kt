package com.example.morningroutine.core.data.repository

import kotlinx.coroutines.flow.Flow
import java.util.Date

interface UserPreferencesRepository {

    // General User Data
    val userDataFlow: Flow<UserData>

    // Specific Finance Data
    val financeUserDataFlow: Flow<FinanceUserData>

    /** Add a Stock Symbol to userPreferences */
    suspend fun addStockSymbol(stockSymbol: String)

    suspend fun clearStocksPrefs()

    /** Add a Routine to userPreferences */
    suspend fun addRoutine(routineType: Int)

    suspend fun updateLastUpdatedTime()
}

data class UserData(
    /** The list of the routines the user registered for */
    val registeredRoutines: List<Int>,
)

data class FinanceUserData(
    /** The list of symbols the user added */
    val stockSymbols: List<String> = emptyList(),
    val lastUpdatedTime: Long? = null,
)