package com.example.morningroutine.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.morningroutine.R
import com.example.morningroutine.ui.routine.StockInfo

sealed class Routine(
    val title: Int,
    val icon: ImageVector,
    val type: Int
) {
    data class RoutineFinance(
        val stocks: List<StockInfo> = emptyList()
    ): Routine(
        title = R.string.finance,
        icon = Icons.Filled.AttachMoney,
        type = RoutineType.FINANCE.type
    )
}

enum class RoutineType(val type: Int) {
    UNKNOWN(0),
    HOME(1),
    FINANCE(2)
}



