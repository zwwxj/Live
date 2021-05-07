package com.my.live.remote.api

import com.my.live.data.entity.Repos
import retrofit2.http.GET

interface LoginApi {
    @GET("/csz/hello")
    suspend fun getResult(): Repos
}