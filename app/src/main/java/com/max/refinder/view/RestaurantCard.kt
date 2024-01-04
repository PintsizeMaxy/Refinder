package com.max.refinder.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
        Row(Modifier.fillMaxWidth()) {
            RestaurantCardField(title = "Name:", value = "Bar 3 BBQ")
            RestaurantCardField(title = "Type:", value = "BBQ")
        }
        Row(Modifier.fillMaxWidth()) {
            RestaurantCardField(title = "Address:", value = "119 E Main St, Belgrade, MT 59714")
            RestaurantCardField(title = "Distance:", value = "2 miles")
        }
        Row(Modifier.fillMaxWidth()) {
            RestaurantCardField(title = "Rating:", value = "4/5")
            RestaurantCardField(title = "Reviews:", value = "231 Reviews")
        }
        Row(Modifier.fillMaxWidth()) {
            RestaurantCardField(title = "Hours:", value = "5pm")
            RestaurantCardField(title = "Distance:", value = "2 miles")
        }
    }
}

@Composable
fun RowScope.RestaurantCardField(title: String, value: String, modifier: Modifier = Modifier){
    Row(modifier = modifier.weight(.5f).fillMaxWidth()){
        Text(title)
        Text(value)
    }
}