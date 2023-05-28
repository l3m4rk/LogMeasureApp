package dev.l3m4rk.logmeasureapp.measurelog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.l3m4rk.logmeasureapp.R

@ExperimentalMaterial3Api
@Composable
fun MeasureLogScreen() {
    val primaryColor = MaterialTheme.colorScheme.primary
    val title = stringResource(R.string.measure_log_title)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title, color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = primaryColor)
            )
        }
    ) { paddingValues ->

//        val imageUri by remember { mutableStateOf<Im() }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            // TODO: show image
//            Image(painter = , contentDescription = )
            Text(text = "Measure Log Screen")
            // TODO: add button to pick an image
        }
    }
}
