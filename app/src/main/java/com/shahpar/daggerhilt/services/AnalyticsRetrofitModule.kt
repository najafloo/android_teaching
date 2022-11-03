package com.shahpar.daggerhilt.services

import com.shahpar.daggerhilt.Const.BASE_URL
import com.shahpar.daggerhilt.retrofit.GitHubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object AnalyticsRetrofitModule {
    @Provides
    fun provideRetrofitService() : GitHubService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
    }
}