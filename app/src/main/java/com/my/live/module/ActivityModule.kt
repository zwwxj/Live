package com.my.live.module

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


/**
 * Activity级别Module
 * @author caishuzhan
 */
@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {
}