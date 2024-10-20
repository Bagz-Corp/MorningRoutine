package com.example.morningroutine.core.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import com.example.morningroutine.core.data.repository.DataStoreRepository.PreferencesKeys.LAST_UPDATED
import com.example.morningroutine.core.data.repository.DataStoreRepository.PreferencesKeys.ROUTINES
import com.example.morningroutine.core.data.repository.DataStoreRepository.PreferencesKeys.STOCK_SYMBOLS
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import java.util.Date
import javax.inject.Inject

private const val TAG = "UserPreferencesRepository"

@OptIn(ExperimentalCoroutinesApi::class)
class DataStoreRepository @Inject constructor(
    private val userPreferencesStore: DataStore<Preferences>, // To be injected
): UserPreferencesRepository {

    private object PreferencesKeys {
        val STOCK_SYMBOLS = stringSetPreferencesKey("stockSymbols")
        val ROUTINES = stringSetPreferencesKey("routines")
        val LAST_UPDATED = longPreferencesKey("lastUpdated")
    }

    override suspend fun addRoutine(routineType: Int) {
        userPreferencesStore.edit { preferences ->
            preferences[ROUTINES] = (preferences[ROUTINES] ?: emptySet()) + routineType.toString()
        }
    }

    override val userDataFlow: Flow<UserData> = userPreferencesStore.data
        .catch { exception ->
            handleError(exception)
        }
        .mapLatest { preferences ->
            Log.i(TAG, "Updated user Data is now: ${preferences[ROUTINES]}")
            val registeredRoutines = preferences[ROUTINES]?.map { it.toInt() } ?: emptyList()
            UserData(
                registeredRoutines = registeredRoutines
            )
        }


    override val financeUserDataFlow: Flow<FinanceUserData> = userPreferencesStore.data
        .catch { exception ->
            handleError(exception)
        }
        .mapLatest { prefs ->
            Log.i(TAG, "Updated Finance User Data is now: ${prefs[STOCK_SYMBOLS]}")
            FinanceUserData(
                stockSymbols = prefs[STOCK_SYMBOLS]?.toList() ?: emptyList(),
                lastUpdatedTime = prefs[LAST_UPDATED]
            )
        }


    override suspend fun addStockSymbol(stockSymbol: String) {
        userPreferencesStore.edit { preferences ->
            val currentSymbols = preferences[STOCK_SYMBOLS] ?: emptySet()
            preferences[STOCK_SYMBOLS] = currentSymbols + stockSymbol.trim()
            Log.i(TAG, "Updated stock list is now: ${preferences[STOCK_SYMBOLS]}")
        }
    }

    override suspend fun clearStocksPrefs() {
        userPreferencesStore.edit { it[STOCK_SYMBOLS] = emptySet() }
        Log.i(TAG, "STOCK_SYMBOLS cleared")
    }

    override suspend fun updateLastUpdatedTime() {
        val time = Date().time
        userPreferencesStore.edit { it[LAST_UPDATED] = time }
        Log.i(TAG, "LAST_UPDATED updated to $time")
    }

    private suspend fun FlowCollector<Preferences>.handleError(
        exception: Throwable
    ) {
        if (exception is IOException) { // error while reading data
            Log.e(TAG, "Error reading preferences.", exception)
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }
}
