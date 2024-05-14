package com.viridis.di

import com.viridis.data.repositories.AirPollutionRepository
import com.viridis.data.repositories.AirPollutionRepositoryImpl
import com.viridis.data.repositories.EcoTrackerRepository
import com.viridis.data.repositories.EcoTrackerRepositoryImpl
import com.viridis.data.repositories.NewsRepository
import com.viridis.data.repositories.NewsRepositoryImpl
import com.viridis.service.ApiService
import com.viridis.service.EcoTrackerService
import com.viridis.service.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://viridis-c8976-default-rtdb.europe-west1.firebasedatabase.app/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(service: ApiService): NewsRepository {
        return NewsRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideEcoTrackerRepository(service: ApiService): EcoTrackerRepository {
        return EcoTrackerRepositoryImpl(service)
    }

    @Provides
    @Singleton
    fun provideAirPollutionRepository(service: ApiService): AirPollutionRepository {
        return AirPollutionRepositoryImpl(service)
    }
}