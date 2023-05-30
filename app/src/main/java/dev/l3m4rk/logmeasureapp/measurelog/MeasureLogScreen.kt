package dev.l3m4rk.logmeasureapp.measurelog

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.l3m4rk.logmeasureapp.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.math.roundToInt

@ExperimentalMaterial3Api
@Composable
fun MeasureLogScreen(viewModel: MeasureLogViewModel = hiltViewModel()) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val title = stringResource(R.string.measure_log_title)

    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val shouldShowFab = imageUri == null

    val pickImage = rememberLauncherForActivityResult(
        contract = PickVisualMedia(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                imageUri = uri
            }
        }
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title, color = MaterialTheme.colorScheme.onPrimary) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = primaryColor)
            )
        },
        floatingActionButton = {
            if (imageUri == null) {
                AnimatedVisibility(shouldShowFab) {
                    ExtendedFloatingActionButton(
                        onClick = { pickImage.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly)) }
                    ) {
                        Text("Pick image")
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // TODO: implement length measuring
            val measuredLength by rememberSaveable { mutableStateOf(0) }
            val diameter by viewModel.diameter.collectAsState()

            Text(text = "Log diameter: $diameter cm")
            Text(text = "Log length: $measuredLength cm")

            val state = rememberTransformableState(onTransformation = { zoomChange, _, _ ->
                viewModel.scale(zoomChange)
            })

            Box(modifier = Modifier
                .fillMaxHeight(0.5f)
                .background(MaterialTheme.colorScheme.secondary)
                .transformable(state)
            ) {

                val circleWidth = 2

                var offsetX by remember { mutableStateOf(0f) }
                var offsetY by remember { mutableStateOf(0f) }

                Image(
                    painter = rememberAsyncImagePainter(imageUri),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
                Canvas(modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                    .size((diameter/2).dp)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            offsetX += dragAmount.x
                            offsetY += dragAmount.y
                        }
                    }
                    .onGloballyPositioned { layoutCoordinates ->

                    }
                ) {
                    drawCircle(Color.Blue, style = Stroke(width = circleWidth.dp.toPx()))
                }
            }
            Row {
                Button(onClick = {
                    viewModel.reset()
                }) {
                    Text("Reset")
                }

                Button(onClick = { /*TODO*/ }) {
                    Text("Save measurement")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MeasureLogScreenPreview() {
    MeasureLogScreen(viewModel())
}

@HiltViewModel
class MeasureLogViewModel @Inject constructor(): ViewModel() {

    private val _radius = MutableStateFlow(DEFAULT_RADIUS)

    val diameter = _radius.map { it * 2 }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(2000),
            DEFAULT_RADIUS
        )


    fun scale(scale: Float) {
        _radius.update {
            (it * scale).toInt()
        }
    }

    fun reset() {
        _radius.value = DEFAULT_RADIUS
    }

    companion object {
        private const val DEFAULT_RADIUS = 80
    }
}
