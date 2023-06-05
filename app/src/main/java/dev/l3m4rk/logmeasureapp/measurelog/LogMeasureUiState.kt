package dev.l3m4rk.logmeasureapp.measurelog

data class LogMeasureUiState(
    val measureType: MeasureType = MeasureType.DIAMETER,
    val diameter: Int = 0,
    val canSaveMeasurement: Boolean = true,
    val canReset: Boolean = true,
    val showError: Boolean = false,
    val isMeasurementSaved: Boolean = false,
    val imageUri: String? = null,
)

enum class MeasureType {
    DIAMETER,
    LENGTH
}
