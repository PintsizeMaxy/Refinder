package com.max.refinder.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.mapbox.geojson.Point
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.DefaultSettingsProvider
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.plugin.gestures.generated.GesturesSettings
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.max.refinder.R
import com.max.refinder.disabledMap
import com.max.refinder.models.ViewState
import com.max.refinder.viewmodels.MapViewModel

@Composable
fun MapScreen(viewModel: MapViewModel, navigateToRestaurants: () -> Unit) {
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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
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
                            }
                        )
                    },
                    locationComponentSettings = LocationComponentSettings(
                        DefaultSettingsProvider.createDefault2DPuck(withBearing = false)
                    ) {
                        enabled = true
                    },
                    gesturesSettings = disabledMap
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier.padding(top = 124.dp),
                        onClick = navigateToRestaurants) {
                        Text(stringResource(R.string.get_restaurants))
                    }
                }
            }
        }
    }
}