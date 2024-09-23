package com.example.morningroutine.core.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.morningroutine.core.data.repository.DataStoreRepository.PreferencesKeys.ROUTINES
import com.example.morningroutine.core.data.repository.DataStoreRepository.PreferencesKeys.STOCK_SYMBOLS
import com.example.morningroutine.model.Routine
import com.example.morningroutine.model.RoutineType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

private const val TAG = "UserPreferencesRepository"

class DataStoreRepository @Inject constructor(
    private val userPreferencesStore: DataStore<Preferences>, // To be injected
): UserPreferencesRepository {
    private object PreferencesKeys {
        val STOCK_SYMBOLS = stringSetPreferencesKey("stockSymbols")
        val ROUTINES = stringSetPreferencesKey("routines")
    }

    override suspend fun addRoutine(routineType: Int) {
        userPreferencesStore.edit { preferences ->
            preferences[ROUTINES] = (preferences[ROUTINES] ?: emptySet()) + routineType.toString()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override val userPreferencesFlow: Flow<UserData> = userPreferencesStore.data
        .catch { exception ->
            if (exception is IOException) { // error while reading data
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .mapLatest { preferences ->
            val stockSymbols = preferences[STOCK_SYMBOLS] ?: emptySet()
            val registeredRoutines = preferences[ROUTINES]?.map { it.toInt() } ?: emptyList()

            Log.i(TAG, "User preferences: $stockSymbols")
            UserData(
                registeredRoutines = registeredRoutines,
                stockSymbols = stockSymbols.toList()
            )
        }

    override suspend fun updateStockSymbols(stockSymbols: List<String>) {
        userPreferencesStore.edit { preferences ->
            preferences[STOCK_SYMBOLS] = stockSymbols.toSet()
        }
    }

    override suspend fun addStockSymbol(stockSymbol: String) {
        userPreferencesStore.edit { preferences ->
            val currentSymbols = preferences[STOCK_SYMBOLS] ?: emptySet()
            preferences[STOCK_SYMBOLS] = currentSymbols + stockSymbol.trim()
            Log.i(TAG, "User preferences: $preferences")
        }
    }

    override suspend fun clearPrefs() {
        userPreferencesStore.edit { it.clear() }
    }
}
