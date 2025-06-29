package com.example.gymjournal.presentations.workout.comp

data class TimerState(
    val totalTime: Int,
    val remainingTime: Int,
    val isRunning: Boolean
) {
    val progressPercentage: Float
        get() = if (totalTime > 0) remainingTime / totalTime.toFloat() else 0f

    val displaySeconds: String
        get() {
            val m = remainingTime / 60
            val s = remainingTime % 60
            return "%02d:%02d".format(m, s)
        }
}