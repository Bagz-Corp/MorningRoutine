package com.example.morningroutine.core.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.model.Routine
import com.example.morningroutine.model.RoutineType
import com.example.morningroutine.ui.routine.StockInfo

// APIs parameters should be reviewed here for scalability

@Composable
fun StockCard(
    modifier: Modifier,
    icon: ImageVector = Icons.Filled.ChevronRight,
    cardInfo: StockInfo
) {
    Column(
        modifier = modifier
    ) {
        ListItem(
            headlineContent = { Text(cardInfo.name) },
            leadingContent = {
                Icon(
                    imageVector = icon,
                    contentDescription = "Localized description",
                )
            },
            trailingContent = {
                Text(
                    text = "Stock Value = ${cardInfo.latestValue}"
                )
            },
            supportingContent = {
                Text(
                    fontStyle = FontStyle.Italic,
                    text = "Last updated Date : ${cardInfo.lastUpdateDateTime}"
                )
            }
        )
        HorizontalDivider()
    }
}

@Composable
fun RoutineCard(
    routineType: RoutineType,
    onCardClick: (RoutineType) -> Unit,
) {
    var isClicked by remember { mutableStateOf(false) }
    val scale = animateFloatAsState(
        targetValue = if (isClicked) 1.1f else 1f,
        label = ""
    )

    val routineItem: Routine = when (routineType) {
        RoutineType.FINANCE -> {
            Routine.RoutineFinance()
        }
        else -> {
            return
        }
    }

    val cardTitle = stringResource(id = routineItem.title)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .scale(scale.value)
            .clickable {
                isClicked = !isClicked
                onCardClick(routineType)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        content = {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(), 
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = routineItem.icon,
                    contentDescription = null
                )
                Text(
                    text = cardTitle,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
        }
    )
}

@Composable
@Preview
fun StockCardPreview() = StockCard(
    modifier = Modifier,
    cardInfo = StockInfo(
        name = "Test",
        latestValue = "123",
        lastUpdateDateTime = java.util.Date(),
        symbol = "TST"
    )
)

@Composable
@Preview
fun RoutineCardPreview() = RoutineCard(
    routineType = RoutineType.FINANCE,
    onCardClick = {}
)
