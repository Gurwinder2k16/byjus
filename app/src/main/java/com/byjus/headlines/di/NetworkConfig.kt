package com.byjus.headlines.di

import com.byjus.headlines.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Module
class NetworkConfig @Inject constructor() {

    private var mRequestTimeOut = 30L

    /**
     * Retrofit Api Implementation
     */
    @Provides
    fun getRetrofit(): Retrofit {
        return initializeRetrofitService(BuildConfig.BASE_URL)
    }

    /**
     * Initialize Retrofit Api Service
     *
     * @param pBaseUrl - Url for Api
     *
     *
     * Build Connection or handshaking with url and Model
     */
    private fun initializeRetrofitService(pBaseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(pBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().apply {
                readTimeout(mRequestTimeOut, TimeUnit.SECONDS)
                connectTimeout(mRequestTimeOut, TimeUnit.SECONDS)
                addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                interceptors().add(
                    HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
                )
            }.build()).build()
    }
}