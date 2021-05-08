package com.my.live.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * @param op 协程体
 * @param e 异常回调
 * @author caishuzhan
 */
 fun ViewModel.apply( op:suspend () -> Unit,  e: (e: Exception) -> Unit) {
    viewModelScope.launch {
        try {
            op()
        } catch (e: Exception) {
            e(e)
        }
    }
}


open public class Dependency {
    var libs = mutableListOf<String>()
    fun implementation(lib: String) {
        libs.add(lib)
    }
}


