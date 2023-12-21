package com.max.refinder

import com.mapbox.common.location.AccuracyLevel
import com.mapbox.common.location.IntervalSettings
import com.mapbox.common.location.LocationProviderRequest
import com.mapbox.common.location.LocationService
import com.mapbox.common.location.LocationServiceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapModule {
    @Provides
    @Singleton
    fun provideLocationService(): LocationService {
        return LocationServiceFactory.getOrCreate()
    }

    @Provides
    @Singleton
    fun providersLocationProviderRequest(): LocationProviderRequest {
        return  LocationProviderRequest.Builder()
            .interval(
                IntervalSettings.Builder().interval(0L).minimumInterval(0L)
                    .maximumInterval(0L).build()
            )
            .displacement(0F)
            .accuracy(AccuracyLevel.HIGHEST)
            .build()
    }
}