package com.example.gymjournal.presentations.routine.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.gymjournal.domain.model.RoutineDay


@Composable
fun RoutineDaySelector(
    days: List<RoutineDay>,
    currentDay: RoutineDay?,
    onDaySelected: (RoutineDay) -> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(days) { day ->
            val isSelected = currentDay?.id == day.id
            Button(
                onClick = { onDaySelected(day) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(day.name) // ✅ Ganti dari day.dayOfWeek ke day.name
            }
        }
    }
}

