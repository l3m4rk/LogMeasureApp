package dev.l3m4rk.logmeasureapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.l3m4rk.logmeasureapp.measurelog.AddLogMeasurementScreen
import dev.l3m4rk.logmeasureapp.measurements.MeasurementsScreen
import dev.l3m4rk.logmeasureapp.navigation.LogMeasureAppDestinations.MEASUREMENTS
import dev.l3m4rk.logmeasureapp.navigation.LogMeasureAppDestinations.MEASURE_LOG

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
            AddLogMeasurementScreen(onBack = {
                navController.navigateUp()
            })
        }
    }
}
