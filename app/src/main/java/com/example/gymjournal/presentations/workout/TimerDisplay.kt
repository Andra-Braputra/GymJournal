package com.example.gymjournal.presentations.workout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gymjournal.presentations.workout.comp.TimerState

@Composable
fun TimerDisplay(timerState: TimerState, toggleStartStop: () -> Unit) {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            progress = { timerState.progressPercentage },
            modifier = Modifier
                .clickable { toggleStartStop() }
                .width(100.dp),
            trackColor = ProgressIndicatorDefaults.circularTrackColor,
        )
        Text(timerState.displaySeconds)
    }
}
