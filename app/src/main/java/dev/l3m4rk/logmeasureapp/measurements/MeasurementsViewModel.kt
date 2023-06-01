package dev.l3m4rk.logmeasureapp.measurements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.l3m4rk.logmeasureapp.data.Measurement
import dev.l3m4rk.logmeasureapp.data.MeasurementsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MeasurementsViewModel @Inject constructor(
    repository: MeasurementsRepository
) : ViewModel() {

    val uiState = repository.measurements
        .map { measurements: List<Measurement> ->
            measurements.map { MeasurementItem(it.id, it.diameter, "Today") }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = MeasurementsUiState(emptyList())
        )
}

data class MeasurementsUiState(
    val measurements: List<MeasurementItem>
)

data class MeasurementItem(
    val id: Long,
    val diameter: Int,
    val createdAt: String,
)
