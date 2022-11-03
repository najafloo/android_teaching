package com.shahpar.daggerhilt.services

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AnalyticsModule {

    @Binds
    abstract fun bindAnalyticService(analyticsServiceImpl: AnalyticsServiceImpl): AnalyticsService

}