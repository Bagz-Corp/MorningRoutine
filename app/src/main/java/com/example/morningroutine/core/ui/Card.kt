package com.example.morningroutine.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RoutineCard(
    modifier: Modifier,
    icon: ImageVector = Icons.Filled.ChevronRight,
    cardTitle: String
) {
    Column(
        modifier = modifier
    ) {
        ListItem(
            headlineContent = { Text(cardTitle) },
            leadingContent = {
                Icon(
                    icon,
                    contentDescription = "Localized description",
                )
            }
        )
        HorizontalDivider()
    }
}

@Composable
@Preview
fun Preview() = RoutineCard(
    modifier = Modifier,
    cardTitle = "Test"
)
