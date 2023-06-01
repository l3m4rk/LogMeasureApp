package dev.l3m4rk.logmeasureapp.measurelog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MeasureLogViewModel @Inject constructor(): ViewModel() {

    private val _radius = MutableStateFlow(DEFAULT_RADIUS)
    private val _showDiameter = MutableStateFlow(false)
    private val _showFab = MutableStateFlow(true)
    private val _state = MutableStateFlow(LogMeasureUiState())

    val uiState: StateFlow<LogMeasureUiState> =
        combine(_radius.map { it * 2 }, _showDiameter, _state, _showFab) { diameter, shouldShow, state, showFab ->
            state.copy(
                showDiameterMeasurer = shouldShow,
                diameter = diameter,
                showFab = showFab
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(2000),
            LogMeasureUiState()
        )


    fun scale(scale: Float) {
        _radius.update {
            (it * scale).toInt()
        }
    }

    fun startDiameterMeasurement() {
        _showDiameter.value = true
    }

    fun reset() {
        _radius.value = DEFAULT_RADIUS
        _showDiameter.value = false
    }

    fun onSaveMeasurement() {
        val radius = _radius.value
        val diameter = radius * 2
        // TODO:
    }

    companion object {
        private const val DEFAULT_RADIUS = 80
    }
}

data class LogMeasureUiState(
    val showDiameterMeasurer: Boolean = false,
    val diameter: Int = 0,
    val canSaveMeasurement: Boolean = true,
    val canReset: Boolean = true,
    val showFab: Boolean = true,
)