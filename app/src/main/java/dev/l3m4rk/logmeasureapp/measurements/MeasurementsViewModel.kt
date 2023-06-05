package dev.l3m4rk.logmeasureapp.measurements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.l3m4rk.logmeasureapp.data.Measurement
import dev.l3m4rk.logmeasureapp.data.MeasurementsRepository
import dev.l3m4rk.logmeasureapp.domain.FormatDataUseCase
import dev.l3m4rk.logmeasureapp.utils.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MeasurementsViewModel @Inject constructor(
    repository: MeasurementsRepository,
    formatData: FormatDataUseCase
) : ViewModel() {

    val uiState = repository.measurements
        .map { measurements: List<Measurement> ->
            measurements.map {
                MeasurementItem(
                    it.id,
                    it.diameter,
                    formatData.invoke(it.date)
                )
            }
        }
        .map { items -> MeasurementsUiState(items) }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed,
            initialValue = MeasurementsUiState(emptyList())
        )
}
