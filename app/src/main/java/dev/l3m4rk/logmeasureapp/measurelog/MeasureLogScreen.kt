@file:OptIn(ExperimentalMaterial3Api::class)

package dev.l3m4rk.logmeasureapp.measurelog

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import dev.l3m4rk.logmeasureapp.R
import kotlin.math.roundToInt

// TODO: rename to AddLogMeasurementScreen
@Composable
fun MeasureLogScreen(viewModel: MeasureLogViewModel = hiltViewModel(), onBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MeasureLogScreen(
        uiState = uiState,
        onScaleChange = viewModel::scale,
        onResetClick = viewModel::reset,
        saveMeasurement = viewModel::saveMeasurement,
        setImageToMeasure = viewModel::setImageToMeasure,
        consumeError = viewModel::consumeError,
        onBack = onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeasureLogScreen(
    uiState: LogMeasureUiState,
    onScaleChange: (scaleChange: Float) -> Unit = {},
    onResetClick: () -> Unit = {},
    saveMeasurement: () -> Unit = {},
    setImageToMeasure: (uri: String) -> Unit = {},
    consumeError: () -> Unit,
    onBack: () -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val title = stringResource(R.string.measure_log_title)

    val pickImage = rememberLauncherForActivityResult(
        contract = PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                setImageToMeasure(uri.toString())
            }
        }
    )

    val snackbarState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            val onPrimary = MaterialTheme.colorScheme.onPrimary
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.menu_back),
                            tint = onPrimary
                        )
                    }
                },
                title = { Text(title, color = onPrimary) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = primaryColor),
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { pickImage.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) }
            ) {
                Text(stringResource(R.string.button_pick_image))
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarState) }
    ) { paddingValues ->

        LaunchedEffect(uiState.isMeasurementSaved) {
            if (uiState.isMeasurementSaved) {
                onBack()
            }
        }

        LaunchedEffect(uiState.showError) {
            if (uiState.showError) {
                snackbarState.showSnackbar("Error during measurement. Try again.")
                consumeError()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: implement length measuring
            val measuredLength by rememberSaveable { mutableStateOf(0) }

            val logDiameter = stringResource(R.string.log_diameter, uiState.diameter)
            Text(logDiameter)
            Text(text = "Log length: $measuredLength cm")

            val state = rememberTransformableState(onTransformation = { zoomChange, _, _ ->
                onScaleChange(zoomChange)
            })

            Box(modifier = Modifier
                .fillMaxHeight(0.5f)
                .background(MaterialTheme.colorScheme.secondary)
                .transformable(state)
                .paint(rememberAsyncImagePainter(uiState.imageUri), contentScale = ContentScale.Crop)
            ) {

                var offsetX by remember { mutableStateOf(0f) }
                var offsetY by remember { mutableStateOf(0f) }

                Canvas(modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                    .size(uiState.diameter.dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                        }
                    }
                ) {
                    val center = Offset(size.width / 2, size.height / 2)
                    drawCircle(Color.Blue, center = center, style = Stroke(width = 2.dp.toPx()))
                }
            }
            val diameterOption = stringResource(R.string.option_diameter)
            val lengthOption = stringResource(R.string.option_length)
            val options = listOf(diameterOption, lengthOption)
            val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                options.forEach { option ->
                    FilterChip(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) },
                        label = { Text(option) }
                    )
                }
            }
            Row {
                Button(
                    onClick = onResetClick,
                    enabled = uiState.canReset,
                ) {
                    Text(stringResource(R.string.button_reset))
                }

                Button(
                    onClick = saveMeasurement,
                    enabled = uiState.canSaveMeasurement
                ) {
                    Text(stringResource(R.string.button_save_measurement))
                }
            }
        }
    }
}

@Preview
@Composable
fun MeasureLogScreenPreview() {
    MeasureLogScreen(
        uiState = LogMeasureUiState(showDiameterMeasurer = true, diameter = 30),
        consumeError = {},
        onBack = {}
    )
}
