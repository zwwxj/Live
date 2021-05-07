package com.my.live.module

import android.app.Application
import android.util.Log
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.my.base.factory.FileFactory
import com.my.live.BuildConfig
import com.my.live.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Cache
import okhttp3.CookieJar
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
    const val LOG_TAG = "http_log"

    const val TIMEOUT_CONNECT = 15000L
    const val TIMEOUT_WRITE = 15000L
    const val TIMEOUT_READ = 15000L

    const val CACHE_SIZE = 1024 * 1024 * 100L

    @Provides
    @Singleton
    fun provideCookie(application: Application): CookieJar = PersistentCookieJar(
        SetCookieCache(),
        SharedPrefsCookiePersistor(application)
    )

    @Provides
    @Singleton
    fun provideCache(application: Application) = Cache(
        FileFactory.getHttpCacheDir(application), CACHE_SIZE
    )

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, context: Application): Retrofit =
        Retrofit.Builder().baseUrl(context.getString(R.string.url_base)).client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create()).build()

    @Provides
    fun provideOkHttpClient(cookie: CookieJar, cache: Cache) = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
        .writeTimeout(TIMEOUT_WRITE, TimeUnit.MILLISECONDS)
        .readTimeout(TIMEOUT_READ, TimeUnit.MILLISECONDS)
        .addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.i(LOG_TAG, message)
            }
        }).apply {
            if (BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BODY
            } else {
                level = HttpLoggingInterceptor.Level.NONE
            }
        })
        .cookieJar(cookie)
        .cache(cache)
        .build()

}