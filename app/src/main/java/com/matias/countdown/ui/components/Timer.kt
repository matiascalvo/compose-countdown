package com.matias.countdown.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matias.countdown.R
import com.matias.countdown.data.TimerDuration

@Preview(showBackground = true)
@Composable
fun Timer(value: TimerDuration) {
    val color = if (value.isSet()) {
        MaterialTheme.colors.onSurface
    } else {
        MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
    }

    Row(verticalAlignment = Alignment.Bottom) {
        TimeValue(value = value.hours, Modifier.alignByBaseline(), color)
        TimeUnit(resource = R.string.hours_short, Modifier.alignByBaseline(), color)
        TimeValue(
            value = value.minutes,
            modifier = Modifier
                .alignByBaseline()
                .padding(start = 8.dp),
            color = color
        )
        TimeUnit(
            resource = R.string.minutes_short,
            modifier = Modifier.alignByBaseline(),
            color = color
        )
        TimeValue(
            value = value.seconds,
            modifier = Modifier
                .alignByBaseline()
                .padding(start = 8.dp),
            color = color
        )
        TimeUnit(
            resource = R.string.seconds_short,
            modifier = Modifier.alignByBaseline(),
            color = color
        )
    }
}

@Composable
private fun TimeValue(value: Int, modifier: Modifier, color: Color) {
    Text(
        text = value.toString().padStart(2, '0'),
        style = MaterialTheme.typography.h3,
        modifier = modifier,
        color = color
    )
}

@Composable
private fun TimeUnit(resource: Int = R.string.seconds_short, modifier: Modifier, color: Color) {
    Text(
        text = stringResource(id = resource),
        style = MaterialTheme.typography.h4,
        modifier = modifier,
        color = color
    )
}
