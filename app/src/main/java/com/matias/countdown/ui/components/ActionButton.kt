package com.matias.countdown.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matias.countdown.R

@Preview(showBackground = true)
@Composable
fun BottomAction(isActive: Boolean = false, onClick: () -> Unit = {}) {

    FloatingActionButton(onClick = onClick, modifier = Modifier.testTag("BottomActionButton")) {
        if (isActive) {
            Icon(
                imageVector = Icons.Default.Stop,
                contentDescription = stringResource(id = R.string.content_stop_timer),
                modifier = Modifier.size(24.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = stringResource(id = R.string.content_start_timer),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
