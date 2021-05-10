package com.my.live.viewmodel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.my.base.component.viewmodel.BaseViewModel
import com.my.base.retrofit.*
import com.my.live.data.entity.Repos
import com.my.live.data.entity.Req
import com.my.live.remote.repository.LoginRepository

/**
 * @author caishuzhan
 */
class LoginViewModel @ViewModelInject constructor(
    val loginRepository: LoginRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val KEY_REQ_1 = "KEY_REQ_1"
    val KEY_REQ_2 = "KEY_REQ_2"
    val KEY_REQ_3 = "KEY_REQ_3"
    val KEY_REQ_4 = "KEY_REQ_4"



    private val repos: MutableLiveData<Repos> = savedStateHandle.getLiveData(KEY_REQ_1)
    val _repos: LiveData<Repos> = repos


    private val retrofit = MediatorLiveData<ResultData<Repos>>()
    val _liveRetrofit: LiveData<ResultData<Repos>>
        get() = retrofit


    private val trans: MutableLiveData<Any> = savedStateHandle.getLiveData(KEY_REQ_3)
    val _Trans = Transformations.switchMap(trans) {
        transforma()
    }

    private val live4: MutableLiveData<Repos> = savedStateHandle.getLiveData(KEY_REQ_4)
    val _live4: LiveData<Repos> = live4

    fun getReposWithCoroutine() {
        apply({
            val result = loginRepository.getResult()
            savedStateHandle.set(KEY_REQ_1, result)
            Log.i("csz", "repos $repos ${_repos.value} ${result}")
        }, {
            Log.i("csz", "error ${it.message} ")
        })
    }

    fun getByRetrofit() {
        val data = viewModelScope.requestLiveData<Repos> {
            api {
                this@LoginViewModel.loginRepository.getResult()
            }
        }
        retrofit.addSourceAutoRemove(data)
    }

    fun getByTransforma() {
        trans.value = Req()
    }

    fun transforma() =
        viewModelScope.requestLiveData<Repos> {
            api {
                this@LoginViewModel.loginRepository.getResult()
            }
        }


    fun getLive4(){
        launch{
            live4.value = this@LoginViewModel.loginRepository.getResult()
        }
    }

}