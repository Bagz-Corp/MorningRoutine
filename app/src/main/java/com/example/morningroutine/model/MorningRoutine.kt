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
        type = RoutineType.FINANCE.type
    )

    object StubRoutine1: Routine(
        title = R.string.stub,
        icon = Icons.Filled.BugReport,
        type = RoutineType.ROUTINE2.type
    )

    object StubRoutine2: Routine(
        title = R.string.stub,
        icon = Icons.Filled.BugReport,
        type = RoutineType.ROUTINE2.type
    )
}

/**
 * ROUTINE2 and ROUTINE3 are here to show expected design on Home Screen. They should be replaced
 * with actual routines when implemented.
 */
enum class RoutineType(val type: Int) {
    UNKNOWN(0),
    HOME(1),
    FINANCE(2),
    ROUTINE2(3),
    ROUTINE3(4)
}



