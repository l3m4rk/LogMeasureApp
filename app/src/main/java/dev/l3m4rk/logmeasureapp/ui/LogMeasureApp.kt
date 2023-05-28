package dev.l3m4rk.logmeasureapp.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.l3m4rk.logmeasureapp.measurements.MeasurementsScreen
import dev.l3m4rk.logmeasureapp.measurelog.MeasureLogScreen
import dev.l3m4rk.logmeasureapp.navigation.LogMeasureAppDestinations.MEASURE_LOG
import dev.l3m4rk.logmeasureapp.navigation.LogMeasureAppDestinations.MEASUREMENTS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogMeasureApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MEASUREMENTS
    ) {
        composable(MEASUREMENTS) {
            MeasurementsScreen(
                onStartMeasureClick = {
                    navController.navigate(MEASURE_LOG)
                }
            )
        }
        composable(MEASURE_LOG) {
            MeasureLogScreen()
        }
    }
}
