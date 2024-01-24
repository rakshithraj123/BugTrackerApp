package com.app.bugtrackerapp.di

import com.app.bugtrackerapp.data.remote.network.RetrofitAPIService
import com.app.bugtrackerapp.data.remote.network.RetrofitInstance
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofitAPIService(): RetrofitAPIService {
        return Retrofit.Builder()
            .client(RetrofitInstance.okHttpClient)
            .baseUrl(RetrofitInstance.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().apply { setLenient() }.create()))
            .build().create(RetrofitAPIService::class.java)
    }

}