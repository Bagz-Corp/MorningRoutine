package com.example.morningroutine.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.morningroutine.R

enum class TopLevelDestinations (
    val selectedIcon: ImageVector,
    val titleTextId: Int
) {
    HOME(
        selectedIcon = Icons.Filled.Home,
        titleTextId = R.string.app_name
    ),
    FINANCE(
        selectedIcon = Icons.Filled.AttachMoney,
        titleTextId = R.string.app_name
    )
}

