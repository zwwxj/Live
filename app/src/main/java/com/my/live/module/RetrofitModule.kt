package com.my.live.module

import android.app.Application
import com.my.live.BuildConfig
import com.my.live.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author caishuzhan
 */
@Module
@InstallIn(ApplicationComponent::class)
object RetrofitModule {

    const val TIMEOUT_CONNECT = 15000L
    const val TIMEOUT_WRITE = 15000L
    const val TIMEOUT_READ = 15000L

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, context: Application): Retrofit =
        Retrofit.Builder().baseUrl(context.getString(R.string.url_base)).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()

    @Provides
    fun provideOkHttpClient(application: Application) = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
        .writeTimeout(TIMEOUT_WRITE, TimeUnit.MILLISECONDS)
        .readTimeout(TIMEOUT_READ, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            } else {
                level = HttpLoggingInterceptor.Level.NONE
            }
        })
        .build()
}