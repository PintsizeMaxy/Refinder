package com.max.refinder.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getDrawable
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.DefaultSettingsProvider
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.CircleAnnotation
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings
import com.max.refinder.R
import com.max.refinder.disabledMap

@OptIn(MapboxExperimental::class)
@Composable
fun RestaurantCard() {
    val context = LocalContext.current
    val drawable = bitmapFromDrawableRes(context, R.drawable.ic_annotation)
    Column(Modifier.fillMaxSize()) {
        MapboxMap(
            Modifier
                .fillMaxWidth()
                .height(248.dp),
            mapInitOptionsFactory = {
                MapInitOptions(
                    context = it,
                    styleUri = Style.MAPBOX_STREETS,
                    cameraOptions = CameraOptions.Builder()
                        .center(Point.fromLngLat(-111.1748432651621, 45.7753242908581))
                        .zoom(9.0)
                        .build()
                )
            },
            locationComponentSettings = LocationComponentSettings(
                DefaultSettingsProvider.createDefault2DPuck(withBearing = false)
            ) {
                enabled = true
            },
            gesturesSettings = disabledMap
        ){
            CircleAnnotation(
                point = Point.fromLngLat(-111.1748432651621, 45.7753242908581),
                circleRadius = 20.0,
                circleColorInt = Color.BLUE,
                onClick = {
                    true
                }
            )
        }
        Row(
            Modifier
                .padding(12.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RestaurantCardField(title = "Name:", value = "Bar 3 BBQ", Modifier.weight(1.2f))
            RestaurantCardField(title = "Type:", value = "BBQ", Modifier.weight(1.2f))
        }
        Row(
            Modifier
                .padding(12.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RestaurantCardField(
                title = "Address:",
                value = "119 E Main St, Belgrade, MT 59714",
                Modifier.weight(1.2f)
            )
            RestaurantCardField(title = "Distance:", value = "2 miles", Modifier.weight(1.2f))
        }
        Row(
            Modifier
                .padding(12.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RestaurantCardField(title = "Rating:", value = "4/5", Modifier.weight(1.2f))
            RestaurantCardField(title = "Reviews:", value = "231 Reviews", Modifier.weight(1.2f))
        }
        Row(
            Modifier
                .padding(12.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RestaurantCardField(title = "Hours:", value = "5pm", Modifier.weight(1.2f))
            RestaurantCardField(title = "Distance:", value = "2 miles", Modifier.weight(1.2f))
        }
    }
}

@Composable
fun RestaurantCardField(title: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(title)
        Text(value)
    }
}

private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
    convertDrawableToBitmap(getDrawable(context, resourceId))

private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
    if (sourceDrawable == null) {
        return null
    }
    return if (sourceDrawable is BitmapDrawable) {
        sourceDrawable.bitmap
    } else {
// copying drawable object to not manipulate on the same reference
        val constantState = sourceDrawable.constantState ?: return null
        val drawable = constantState.newDrawable().mutate()
        val bitmap: Bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth, drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        bitmap
    }
}