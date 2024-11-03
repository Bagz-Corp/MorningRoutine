package com.example.morningroutine.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BugReport
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
        type = Destination.FINANCE.type
    )

    object StubRoutine1: Routine(
        title = R.string.stub,
        icon = Icons.Filled.BugReport,
        type = Destination.ROUTINE2.type
    )

    object StubRoutine2: Routine(
        title = R.string.stub,
        icon = Icons.Filled.BugReport,
        type = Destination.ROUTINE2.type
    )
}

/**
 * ROUTINE2 and ROUTINE3 are here to show expected design on Home Screen. They should be replaced
 * with actual routines when implemented.
 */
enum class Destination(
    val type: Int
) {
    UNKNOWN(type = 0),
    HOME(type = 1),
    FINANCE(type = 2),
    STOCK_DETAILS(type = 3),
    ROUTINE2(type = 4);

    companion object {
        fun from(type: Int): Destination = when (type) {
            1 -> HOME
            2 -> FINANCE
            3 -> STOCK_DETAILS
            4 -> ROUTINE2
            else -> UNKNOWN
        }
    }
}
