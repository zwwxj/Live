package com.my.base.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * @author caishuzhan
 */
fun <T> MediatorLiveData<ResultData<T>>.addSourceAutoRemove(liveData: LiveData<ResultData<T>>) {
    this.addSource(liveData) {
        if (it.requestStatus == RequestStatus.COMPLETE) {
            this.removeSource(liveData)
        }
        this.value = it
    }
}