package dev.l3m4rk.logmeasureapp.measurements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddChart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.l3m4rk.logmeasureapp.R

@Composable
fun MeasurementsScreen(
    viewModel: MeasurementsViewModel = hiltViewModel(),
    onStartMeasureClick: () -> Unit = {}
) {
    val state by viewModel.uiState.collectAsState()
    MeasurementsScreen(
        state = state,
        onStartMeasureClick = onStartMeasureClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasurementsScreen(state: MeasurementsUiState, onStartMeasureClick: () -> Unit = {}) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val title = stringResource(id = R.string.measurements_title)
    val measureAction = stringResource(R.string.button_measure)
    val noMeasurements = stringResource(R.string.empty_measurements_message)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title, color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = primaryColor)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(measureAction) },
                icon = {
                    Icon(
                        Icons.Default.AddChart,
                        contentDescription = stringResource(R.string.cd_start_measure)
                    )
                },
                onClick = onStartMeasureClick
            )
        }
    ) { paddingValues ->
        val items = state.measurements
        if (items.isEmpty()) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(noMeasurements)
            }
        } else {
            MeasurementsContent(paddingValues, items)
        }
    }
}

@Composable
private fun MeasurementsContent(
    paddingValues: PaddingValues,
    items: List<MeasurementItem>
) {
    Column(Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        LazyColumn(
            Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items, key = { item -> item.id }) { item: MeasurementItem ->
                Measurement(item)
            }
        }
    }
}

@Composable
fun Measurement(item: MeasurementItem, onItemClick: () -> Unit = {}) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
    ) {
        Text("Measurement ${item.id}")
        Text("Log diameter ${item.diameter} cm")
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Type: Diameter")
            Text(item.createdAt)
        }
    }
}

@Preview
@Composable
fun MeasurementPreview() {
    Measurement(item = MeasurementItem(12, 23, "Today"))
}

@Preview
@Composable
fun MeasurementsScreenPreview() {
    MeasurementsScreen()
}
