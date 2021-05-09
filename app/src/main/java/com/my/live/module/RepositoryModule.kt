package com.my.live.module

import android.app.Application
import android.util.Log
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.my.base.factory.FileFactory
import com.my.live.BuildConfig
import com.my.live.R
import com.my.live.remote.api.LoginApi
import com.my.live.remote.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
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
 * 接口仓库module
 * [让接口仓库可以在任意地方被注入使用，对所有仓库提供靶点]
 * @author caishuzhan
 */


@EntryPoint
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {

    fun getLoginRepo(): LoginRepository

}

