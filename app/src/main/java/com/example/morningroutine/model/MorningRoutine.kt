package com.example.morningroutine.model

/**
 * Represents the different routines.
 * TODO() : stored them with DataStore
 */
enum class MorningRoutine (
    val title: String,
) {
    FINANCE(
        title = "Finance",
    ),
    CLEAR_PREF(title = "Clear preferences")
}
