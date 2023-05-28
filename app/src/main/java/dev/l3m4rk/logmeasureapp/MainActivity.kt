package dev.l3m4rk.logmeasureapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import dev.l3m4rk.logmeasureapp.ui.LogMeasureApp
import dev.l3m4rk.logmeasureapp.ui.theme.LogMeasureAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LogMeasureAppTheme {
                LogMeasureApp(Modifier.fillMaxSize())
            }
        }
    }
}
