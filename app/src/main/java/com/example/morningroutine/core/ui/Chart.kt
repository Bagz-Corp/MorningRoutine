package com.example.morningroutine.core.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.morningroutine.core.theme.DarkGreenGray10
import com.example.morningroutine.core.theme.Green80
import com.example.morningroutine.core.theme.Red80
import java.time.LocalDate

/**
 *  Composable Component displaying a simple chart from the values given.
 *  x axis should be based on the [DateRange] enum class
 *  y axis represents the values given
 */
@Composable
fun Chart(
    values: List<Float>,
    dateRange: DateRange,
    modifier: Modifier = Modifier
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

    // Update line color according to values evolution
    val lineColor =
        if (shownValues.last() > shownValues.first()) Green80 else Red80

    val dateAxis = @Composable {
        // Text(text = "COUCOU", Modifier.background(Color.Red).width(200.dp))
        DateRow(
            modifier = Modifier.width(700.dp),
            dateRange = dateRange
        )
    }

    val valueAxis = @Composable {
        ValueColumn(
            modifier = Modifier.height(400.dp),
            min = min,
            max = max
        )
    }

    val graph = @Composable {
        Row(
            modifier = Modifier
            .width(700.dp)
            .height(400.dp)
        ) {
            Graph(
                modifier = modifier.fillMaxHeight().weight(1f),
                zipValues = zipValues,
                max = max,
                min = min,
                lineColor = lineColor
            )
        }
    }

    Layout(
        contents = listOf(dateAxis, valueAxis, graph),
        modifier = modifier.padding(16.dp)
    ) { (dateAxis, valueAxis, graph), constraints ->
        // Measurement
        val datePlaceable = dateAxis.first().measure(constraints)
        val valuePlaceable = valueAxis.first().measure(constraints)
        val graphPlaceable = graph.first().measure(constraints)

        val totalWidth = graphPlaceable.width + valuePlaceable.width
        val totalHeight = valuePlaceable.height + datePlaceable.height

        // Placement
        layout(
            width = totalWidth,
            height = totalHeight
        ) {
            datePlaceable.place(x = valuePlaceable.width, y = valuePlaceable.height)
            valuePlaceable.place(x = 0, y = 0)
            graphPlaceable.place(x = valuePlaceable.width, y = 0)
        }
    }
}

@Composable
fun Graph(
    modifier: Modifier = Modifier,
    zipValues: List<Pair<Float, Float>>,
    max: Float,
    min: Float,
    lineColor: Color
) {
    zipValues.forEach { pair ->
        val fromValuePercentage = getValuePercentageForRange(pair.first, max, min)
        val toValuePercentage = getValuePercentageForRange(pair.second, max, min)

        Canvas(
            modifier = modifier
                .background(Color.Blue),
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

@Composable
fun DateRow(modifier: Modifier = Modifier, dateRange: DateRange) {
    val dateList: List<String> = mutableListOf<String>().apply {
        when (dateRange) {
            DateRange.WEEK -> {
                for (i in 0..6) {
                    add(LocalDate.now().minusDays(i.toLong()).dayOfWeek.name)
                }
            }

            DateRange.MONTH -> {
                for (i in 0..9) {
                    add(LocalDate.now().minusDays(i * 3.toLong()).dayOfWeek.name)
                }
            }

            DateRange.YEAR -> {
                for (i in 0..11) {
                    add(LocalDate.now().minusMonths(i.toLong()).month.name)
                }
            }
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        dateList.asReversed().forEach { date ->
            Text(
                modifier = Modifier
                    .height(30.dp)
                    .padding(6.dp),
                textAlign = TextAlign.Center,
                text = date.substring(0, 3),
                color = DarkGreenGray10,
            )
        }
    }
}

@Composable
fun ValueColumn(
    modifier: Modifier = Modifier,
    min: Float,
    max: Float
) {
    val valueList: List<String> = mutableListOf<String>().apply {
        for (i in min.toInt() .. max.toInt() step getStepFrom(max, min)) {
            add(i.toString())
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        valueList.asReversed().forEach {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(30.dp)
                    .padding(vertical = 6.dp),
                color = DarkGreenGray10
            )
        }
    }
}

/** Specify the range of the chart to be covered */
enum class DateRange {
    WEEK, MONTH, YEAR
}

private fun getValuePercentageForRange(value: Float, max: Float, min: Float): Float =
    (value - min) / (max - min)

private fun getStepFrom(max: Float, min: Float): Int {
    val diff = max - min
    return if (diff < 10) {
        1
    } else {
        (diff / 10).toInt()
    }
}

@Preview
@Composable
private fun ChartPreview() {
    Chart(
        modifier = Modifier.background(Color.White),
        values = listOf(10f, 2f, 3f, 4f, -5f),
        dateRange = DateRange.WEEK
    )
}
