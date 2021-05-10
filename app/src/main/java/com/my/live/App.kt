package com.my.live

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.my.base.util.ContextUtils
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application(){

    companion object {
        lateinit var instance: App
            private set
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
            "".orEmpty()
        ContextUtils.init(this)
    }
}