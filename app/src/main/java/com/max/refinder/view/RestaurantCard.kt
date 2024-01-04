package com.max.refinder.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.DefaultSettingsProvider
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.max.refinder.disabledMap

@OptIn(MapboxExperimental::class)
@Composable
fun RestaurantCard() {
    Column(Modifier.fillMaxSize()) {
        MapboxMap(
            Modifier
                .fillMaxWidth()
                .height(248.dp),
            mapViewportState = MapViewportState().apply {
                flyTo(
                    cameraOptions {
                        zoom(8.0)
                        center(
                            Point.fromLngLat(
                                -111.042931,
                                45.676998
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
        ) {

        }
        Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            RestaurantCardField(title = "Name:", value = "Bar 3 BBQ", Modifier.weight(1.2f))
            RestaurantCardField(title = "Type:", value = "BBQ", Modifier.weight(1.2f))
        }
        Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            RestaurantCardField(title = "Address:", value = "119 E Main St, Belgrade, MT 59714", Modifier.weight(1.2f))
            RestaurantCardField(title = "Distance:", value = "2 miles", Modifier.weight(1.2f))
        }
        Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            RestaurantCardField(title = "Rating:", value = "4/5", Modifier.weight(1.2f))
            RestaurantCardField(title = "Reviews:", value = "231 Reviews", Modifier.weight(1.2f))
        }
        Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            RestaurantCardField(title = "Hours:", value = "5pm", Modifier.weight(1.2f))
            RestaurantCardField(title = "Distance:", value = "2 miles", Modifier.weight(1.2f))
        }
    }
}

@Composable
fun RestaurantCardField(title: String, value: String, modifier: Modifier = Modifier){
    Column(modifier){
        Text(title)
        Text(value)
    }
}