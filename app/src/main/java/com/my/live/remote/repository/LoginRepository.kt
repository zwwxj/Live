package com.my.live.remote.repository

import com.my.base.component.repository.BaseRepository
import com.my.live.data.entity.Repos
import com.my.live.remote.api.LoginApi
import javax.inject.Inject

class LoginRepository  @Inject constructor(private val api: LoginApi) :BaseRepository(),LoginApi{

    override suspend fun getResult(): Repos {
        return api.getResult()
    }

}