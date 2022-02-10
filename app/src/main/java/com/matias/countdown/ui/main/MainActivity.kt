package com.matias.countdown.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matias.countdown.R
import com.matias.countdown.data.TimerDuration
import com.matias.countdown.ui.components.BottomAction
import com.matias.countdown.ui.components.Keyboard
import com.matias.countdown.ui.components.Timer
import com.matias.countdown.ui.theme.CountDownTheme

@ExperimentalFoundationApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    private val countdownViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountDownTheme {
                MainApp(countdownViewModel)
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun MainApp(viewModel: MainViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        MainScreen(viewModel)
    }
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun MainScreen(viewModel: MainViewModel) {

    val duration: TimerDuration by viewModel.timerDuration.observeAsState(TimerDuration(0, 0, 0))
    val ticking: Boolean by viewModel.ticking.observeAsState(false)
    val progress: Float by viewModel.progress.observeAsState(0f)

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(id = R.string.countdown), style = MaterialTheme.typography.h5)

        Spacer(Modifier.size(32.dp))

        Timer(duration)

        Spacer(Modifier.size(16.dp))

        Divider()

        if (ticking) {
            Spacer(modifier = Modifier.size(48.dp))
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.testTag("ProgressIndicator")
            )
        } else {
            Keyboard(
                onNumberClick = { number -> viewModel.appendToDuration(number) },
                onBackSpaceClick = { viewModel.backspace() }
            )
        }

        Spacer(modifier = Modifier.size(32.dp))

        AnimatedVisibility(
            visible = duration.isSet(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            BottomAction(
                isActive = ticking,
                onClick = { if (ticking) viewModel.stopTimer() else viewModel.startTimer() }
            )
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    CountDownTheme(darkTheme = false) {
        MainApp(MainViewModel())
    }
}

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    CountDownTheme(darkTheme = true) {
        MainApp(MainViewModel())
    }
}
