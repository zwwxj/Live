package com.my.base.component.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.base.retrofit.ApiException
import com.zs.base_library.common.toast
import kotlinx.coroutines.*



typealias errorFunction = (e: ApiException) -> Unit


/**
 * 所有业务模块viewmodel的父类
 * @author caishuzhan
 */
open class BaseViewModel : ViewModel() {

    private val errorLiveData = MutableLiveData<ApiException>()
    open val _error: LiveData<ApiException> = errorLiveData


    fun handleError(e: Throwable) {
        val error = ApiException.getApiException(e)
      //  toast(error.errorMessage)
        errorLiveData.postValue(error)
    }

    protected fun <T> launch(
        block: () -> T, error: errorFunction? = null
    ) {
        viewModelScope.launch {
            runCatching {
                block()
            }.onFailure {
                it.printStackTrace()
                ApiException.getApiException(it).apply {
                    withContext(Dispatchers.Main) {
                        error?.invoke(this@apply)
                    }
                }
            }
        }
    }

    protected fun <T> launch(block: suspend () -> T) {
        launchAndToast(block)
    }

    protected fun <T> launchAndToast(block: suspend () -> T,toast: Boolean = false) {
        viewModelScope.launch {
            runCatching {
                block()
            }.onFailure {
                it.printStackTrace()
                ApiException.getApiException(it).apply {
                    withContext(Dispatchers.Main) {
                        errorLiveData.value = this@apply
                        if (toast){
                            toast(errorMessage)
                        }
                    }
                }
            }
        }
    }


}