package dev.l3m4rk.logmeasureapp.measurelog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.l3m4rk.logmeasureapp.data.MeasurementsRepository
import dev.l3m4rk.logmeasureapp.utils.WhileSubscribed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeasureLogViewModel @Inject constructor(
    private val repository: MeasurementsRepository
): ViewModel() {

    private val _radius = MutableStateFlow(DEFAULT_RADIUS)
    private val _showDiameter = MutableStateFlow(false)
    private val _showFab = MutableStateFlow(true)
    private val _state = MutableStateFlow(LogMeasureUiState())
    private val state = _state.asStateFlow()
    private val _showError = MutableStateFlow(false)
    private val _imageUri = MutableStateFlow<String?>(null)

    val uiState: StateFlow<LogMeasureUiState> =
        combine(
            _radius.map { it * 2 },
            _showDiameter,
            _state,
            _showFab,
            _showError
        ) { diameter, shouldShow, state, showFab, showError ->
            state.copy(
                showDiameterMeasurer = shouldShow,
                diameter = diameter,
                showFab = showFab,
                showError = showError
            )
        }
        .stateIn(
            viewModelScope,
            WhileSubscribed,
            LogMeasureUiState()
        )


    fun scale(scale: Float) {
        _radius.update { radius ->
            (radius * scale).toInt()
        }
    }

    fun setImageToMeasure(uri: String) {
        _imageUri.value = uri
    }

    fun startDiameterMeasurement() {
        _showDiameter.value = true
    }

    fun consumeError() {
        _showError.value = false
    }

    fun reset() {
        _radius.value = DEFAULT_RADIUS
        _showDiameter.value = false
    }

    fun saveMeasurement() {
        val radius = _radius.value
        val diameter = radius * 2

        if (diameter == 0 || _imageUri.value == null) {
            _showError.value = true
            return
        }

        viewModelScope.launch {
            repository.addDiameterMeasurement(diameter)
        }
        _state.update {
            it.copy(isMeasurementSaved = true)
        }
    }

    companion object {
        private const val DEFAULT_RADIUS = 80
    }
}
