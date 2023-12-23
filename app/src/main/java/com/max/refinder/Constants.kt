package com.max.refinder

import com.mapbox.maps.plugin.gestures.generated.GesturesSettings

val disabledMap = GesturesSettings {
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