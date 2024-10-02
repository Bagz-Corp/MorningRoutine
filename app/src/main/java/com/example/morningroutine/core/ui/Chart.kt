package com.example.morningroutine.core.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.core.theme.Green80
import com.example.morningroutine.core.theme.Red80

@Composable
fun Chart(
    values: List<Float>,
    dateRange: DateRange
) {
    // Get number of x points
    val listSize = when(dateRange) {
        DateRange.WEEK -> 7
        DateRange.MONTH -> 30
        DateRange.YEAR -> 365
    }

    // If we don't get enough values, fill with 0
    val valuesSize = values.size
    val shownValues = if (valuesSize < listSize) {
        val zeros = List(listSize-valuesSize, init = { 0f })
        zeros+values
    } else {
        values.subList(0, listSize)
    }

    // Create points
    val zipValues: List<Pair<Float, Float>> = shownValues.zipWithNext()

    val min = shownValues.min()
    val max = shownValues.max()

    val lineColor =
        if (shownValues.last() > shownValues.first()) Green80 else Red80

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .background(Color.Gray),
    ) {
        zipValues.forEach { pair ->
            val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
            val toValuePercentage = getValuePercentageForRange(pair.second, max, min)

            Canvas(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                onDraw = {
                    val fromPoint = Offset(
                        x = 0f,
                        y = size.height.times(1 - fromValuePercentage)
                    )

                    val toPoint = Offset(
                        x = size.width,
                        y = size.height.times(1 - toValuePercentage)
                    )


                    drawLine(
                        start = fromPoint,
                        end = toPoint,
                        color = lineColor,
                        strokeWidth = 8f
                    )
                }
            )
        }
    }
}


enum class DateRange {
    WEEK, MONTH, YEAR
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float): Float =
    (value - min) / (max - min)

@Preview
@Composable
private fun ChartPreview() {
    Chart(
        values = listOf(10f, 2f, 3f, 4f, -5f),
        dateRange = DateRange.WEEK
    )
}
