package com.max.refinder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.DefaultSettingsProvider
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.max.refinder.models.ViewState
import com.max.refinder.viewmodels.MapViewModel

@Composable
fun MapScreen(viewModel: MapViewModel) {
    val locationState = viewModel.mapState.collectAsState().value
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (locationState) {
            ViewState.Failed -> {
                Text("Error")
            }

            ViewState.Loading -> {
                CircularProgressIndicator()
            }

            is ViewState.Success -> {
                MapboxMap(
                    Modifier.fillMaxSize(),
                    mapViewportState = MapViewportState().apply {
                        flyTo(
                            cameraOptions {
                                zoom(8.0)
                                center(
                                    Point.fromLngLat(
                                        locationState.data.long,
                                        locationState.data.lat
                                    )
                                )
                                pitch(0.0)
                                bearing(0.0)
                            },
                            MapAnimationOptions.mapAnimationOptions {
                                duration(12_000)
                            }
                        )
                    },
                    locationComponentSettings = LocationComponentSettings(
                        DefaultSettingsProvider.createDefault2DPuck(withBearing = false)
                    ) {
                        enabled = true
                    },
                    gesturesSettings = GesturesSettings {
                        doubleTapToZoomInEnabled = false
                        doubleTouchToZoomOutEnabled = false
                        pitchEnabled = false
                        pinchScrollEnabled = false
                        simultaneousRotateAndPinchToZoomEnabled = false
                        scrollEnabled = false
                        scrollDecelerationEnabled = false
                        rotateEnabled = false
                        quickZoomEnabled = false
                        rotateDecelerationEnabled = false
                        pinchToZoomEnabled = false
                        pinchToZoomDecelerationEnabled = false
                    }
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(modifier = Modifier.padding(top = 124.dp), onClick = { }) {
                        Text("Get Restaurants")
                    }
                }
            }
        }
    }
}