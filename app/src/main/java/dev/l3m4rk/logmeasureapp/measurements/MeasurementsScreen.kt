package dev.l3m4rk.logmeasureapp.measurements

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.l3m4rk.logmeasureapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasurementsScreen(onStartMeasureClick: () -> Unit) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val title = stringResource(id = R.string.measurements_title)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title, color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = primaryColor)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Measure") },
                icon = { Icon(Icons.Default.AddChart, contentDescription = "Start measure") },
                onClick = onStartMeasureClick
            )
        }
    ) { paddingValues ->
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "No measurements")
        }
        Column(Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
        }
    }
}

@Preview
@Composable
fun MeasurementsScreenPreview() {
    MeasurementsScreen(onStartMeasureClick = {})
}
