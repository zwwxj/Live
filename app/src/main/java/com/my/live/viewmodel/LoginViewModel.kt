package com.my.live.viewmodel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.my.base.retrofit.*
import com.my.live.data.entity.Repos
import com.my.live.remote.api.LoginApi

/**
 * @author caishuzhan
 */
class LoginViewModel @ViewModelInject constructor(
    private val api: LoginApi,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var repos: MutableLiveData<Repos> = MutableLiveData()
    val _repos: LiveData<Repos> = repos


    private val retrofit = MediatorLiveData<ResultData<Repos>>()
    val _liveRetrofit: LiveData<ResultData<Repos>>
        get() = retrofit


    fun getReposWithCoroutine() {
        apply({
            val result = api.getResult()
            repos.value = result
            Log.i("csz", "repos $repos ")
        }, {
            Log.i("csz", "error ${it.message} ")
        })
    }

    fun getByRetrofit(){
        val liveData = viewModelScope.requestLiveData<Repos> {
            api {
                this@LoginViewModel.api.getResult()
            }

        }
        retrofit.addSourceAutoRemove(liveData)
    }
}