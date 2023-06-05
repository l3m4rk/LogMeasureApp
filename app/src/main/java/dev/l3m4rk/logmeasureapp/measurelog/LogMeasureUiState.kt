package dev.l3m4rk.logmeasureapp.measurelog

data class LogMeasureUiState(
    val showDiameterMeasurer: Boolean = false,
    val diameter: Int = 0,
    val canSaveMeasurement: Boolean = true,
    val canReset: Boolean = true,
    val showFab: Boolean = true,
    val showError: Boolean = false,
)