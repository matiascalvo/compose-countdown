package com.matias.countdown.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private const val GRID_WIDTH = 3
private const val COMPLETE_GRID_COUNT = GRID_WIDTH * 3
private const val KEY_ASPECT_RATIO = 1.2f

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun Keyboard(onNumberClick: (Int) -> Unit = {}, onBackSpaceClick: () -> Unit = {}) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxWidth().testTag("Keyboard"),
        contentPadding = PaddingValues(8.dp),
        cells = GridCells.Fixed(GRID_WIDTH)
    ) {
        items(COMPLETE_GRID_COUNT) { index ->
            NumberKey(index + 1, onClick = onNumberClick)
        }
        item {
            item {
                NumberKey(number = 0, onClick = onNumberClick)
            }
            item {
                BackSpaceKey(onClick = onBackSpaceClick)
            }
        }
    }
}

@Composable
fun NumberKey(number: Int, onClick: (Int) -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(KEY_ASPECT_RATIO, false)
            .clickable { onClick(number) }
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            fontSize = MaterialTheme.typography.h4.fontSize,
        )
    }
}

@Composable fun BackSpaceKey(onClick: () -> Unit = {}) {
    Box(
        Modifier
            .testTag("BackSpace")
            .fillMaxWidth()
            .aspectRatio(KEY_ASPECT_RATIO, false)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Backspace,
            contentDescription = Icons.Default.ArrowBack.name,
        )
    }
}
