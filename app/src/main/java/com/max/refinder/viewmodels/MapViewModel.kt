package com.max.refinder.viewmodels

import androidx.lifecycle.ViewModel
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationService
import com.max.refinder.ext.isTrue
import com.max.refinder.models.MapState
import com.max.refinder.models.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val locationService: LocationService,
    private val locationProviderRequest: LocationProviderRequest
) : ViewModel() {

    private val _mapState = MutableStateFlow<ViewState<MapState>>(ViewState.Loading)
    val mapState = _mapState.asStateFlow()

    init {
        retrieveCurrentLocation()
    }

    private fun retrieveCurrentLocation() {
        val locationProviderResult = locationService.getDeviceLocationProvider(locationProviderRequest)
        locationProviderResult.isValue.isTrue(
            {
                locationProviderResult.value!!.getLastLocation {
                    it?.let { location ->
                        _mapState.update {
                            ViewState.Success(MapState(location.latitude, location.longitude))
                        }
                    }
                }
            },
            {
                Timber.e("Error getting device location provider")
            }
        )
    }

}