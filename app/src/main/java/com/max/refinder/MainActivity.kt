package com.max.refinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.DeviceLocationProvider
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.DefaultSettingsProvider
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.plugin.animation.MapAnimationOptions.Companion.mapAnimationOptions
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.max.refinder.ui.theme.RefinderTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RefinderTheme {
                val locationService: LocationService = LocationServiceFactory.getOrCreate()
                var locationProvider: DeviceLocationProvider? = null
                var currentLocation by remember { mutableStateOf(Pair(42.20897824, -88.35683736)) }
                val request = LocationProviderRequest.Builder()
                    .interval(
                        IntervalSettings.Builder().interval(0L).minimumInterval(0L)
                            .maximumInterval(0L).build()
                    )
                    .displacement(0F)
                    .accuracy(AccuracyLevel.HIGHEST)
                    .build();

                val result = locationService.getDeviceLocationProvider(request)
                if (result.isValue) {
                    locationProvider = result.value!!
                } else {
//                    Log.error("Failed to get device location provider")
                }
                locationProvider?.getLastLocation {
                    currentLocation = Pair(it?.latitude ?: 0.0, it?.longitude ?: 0.0)
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MapboxMap(
                        Modifier.fillMaxSize(),
                        mapViewportState = MapViewportState().apply {
                            if(currentLocation.second != 0.0 && currentLocation.first != 0.0) {
                                flyTo(cameraOptions {
                                    zoom(10.0)
                                    center(
                                        Point.fromLngLat(
                                            currentLocation.second,
                                            currentLocation.first
                                        )
                                    )
                                    pitch(0.0)
                                    bearing(0.0)
                                },
                                    mapAnimationOptions {
                                        duration(12_000)
                                    }
                                )
                            }
                        },
                        locationComponentSettings = LocationComponentSettings(
                            DefaultSettingsProvider.createDefault2DPuck(withBearing = false)
                        ) {
                            enabled = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RefinderTheme {
        Greeting("Android")
    }
}