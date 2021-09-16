package com.ftresearch.cakes.di

import com.ftresearch.cakes.BuildConfig
import com.ftresearch.cakes.rest.cake.CakeService
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
abstract class RestModule {

    companion object {

        @Provides
        @Singleton
        fun providesCakeInterface(): CakeService {
            val okHttpClient = OkHttpClient.Builder().build()
            val builder = GsonBuilder()
            val retrofit =
                Retrofit.Builder().baseUrl(BuildConfig.CAKE_SERVER_URL).client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
            return retrofit.create(CakeService::class.java)
        }
    }
}
