package com.my.base.component.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * DataBinding的封装
 * @author caishuzhan
 */
abstract class BaseDataBindingFragment <T : ViewDataBinding>: BaseFragment() {

    protected lateinit var binding: T

    override fun bindWhenOnCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = bindContentView(inflater,container)
        return binding.root
    }

    fun bindContentView(inflater: LayoutInflater, container: ViewGroup?): T =
        DataBindingUtil.inflate<T>(inflater,getLayoutId(),container,false).apply {
            lifecycleOwner = this@BaseDataBindingFragment
        }

    abstract fun getLayoutId(): Int
}