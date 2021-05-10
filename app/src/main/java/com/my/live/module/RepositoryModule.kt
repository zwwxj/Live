package com.my.live.module

import com.my.live.remote.repository.LoginRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

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

