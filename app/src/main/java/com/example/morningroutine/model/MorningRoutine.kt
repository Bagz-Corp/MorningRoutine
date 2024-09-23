package com.example.morningroutine.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.morningroutine.R
import com.example.morningroutine.ui.routine.StockInfo

/**
 * Represents the different routines.
 */
enum class MorningRoutine (
    val title: String,
) {
    FINANCE(
        title = "Finance",
    ),
}

interface Routine {
    val title: Int
    val icon: ImageVector
    val type: Int
}

enum class RoutineType(val type: Int) {
    UNKNOWN(0),
    FINANCE(1)
}

data class RoutineFinance(
    override val title: Int = R.string.finance,
    override val icon: ImageVector = Icons.Filled.AttachMoney,
    override val type: Int = RoutineType.FINANCE.type,
    val stocks: List<StockInfo> = emptyList()
) : Routine

