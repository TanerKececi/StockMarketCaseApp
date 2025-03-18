package com.example.stockmarketcaseapp.di.module

import android.app.Application
import android.content.Context
import com.example.stockmarketcaseapp.BuildConfig
import com.example.stockmarketcaseapp.repository.remote.StocksApiService
import com.example.stockmarketcaseapp.repository.remote.StocksRepository
import com.example.stockmarketcaseapp.repository.remote.StocksRepositoryImpl
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
class NetworkModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val baseUrl = BuildConfig.apiKey ?: throw IllegalStateException("BASE_URL not found in local.properties")
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideStockService(retrofit: Retrofit): StocksApiService {
        return retrofit.create(StocksApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideStockRepository(stockService: StocksApiService): StocksRepository {
        return StocksRepositoryImpl(stockService)
    }
}