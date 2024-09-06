package com.example.morningroutine.core.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.morningroutine.core.data.repository.UserPreferencesRepository.PreferencesKeys.STOCK_SYMBOLS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

private const val TAG = "UserPreferencesRepository"

class UserPreferencesRepository(
    private val userPreferencesStore: DataStore<Preferences>, // To be injected
) {
    private object PreferencesKeys {
        val STOCK_SYMBOLS = stringSetPreferencesKey("stockSymbols")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val userPreferencesFlow: Flow<UserPreferences> = userPreferencesStore.data
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
            Log.i(TAG, "User preferences: $stockSymbols")
            UserPreferences(stockSymbols.toList())
        }

    suspend fun updateStockSymbols(stockSymbols: List<String>) {
        userPreferencesStore.edit { preferences ->
            preferences[STOCK_SYMBOLS] = stockSymbols.toSet()
        }
    }

    suspend fun addStockSymbol(stockSymbol: String) {
        userPreferencesStore.edit { preferences ->
            val currentSymbols = preferences[STOCK_SYMBOLS] ?: emptySet()
            preferences[STOCK_SYMBOLS] = currentSymbols + stockSymbol.trim()
            Log.i(TAG, "User preferences: $preferences")
        }
    }
}

data class UserPreferences(
    val stockSymbols: List<String>
)
