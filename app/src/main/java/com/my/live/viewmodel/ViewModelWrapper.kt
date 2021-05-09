package com.my.live.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.live.data.entity.Req
import kotlinx.coroutines.launch

/**
 * @param op 协程体
 * @param e 异常回调
 * @author caishuzhan
 */
fun ViewModel.apply(op: suspend () -> Unit, e: (e: Exception) -> Unit) {
    viewModelScope.launch {
        try {
            op()
        } catch (e: Exception) {
            e(e)
        }
    }
}

/**
 * 通知请求
 */
fun MutableLiveData<Any>.req() {
    this.value = Req()
}




