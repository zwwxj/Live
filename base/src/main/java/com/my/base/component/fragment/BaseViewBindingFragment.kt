package com.my.base.component.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding

/**
 * ViewBinding的封装
 * @author caishuzhan
 */
abstract class BaseViewBindingFragment <T : ViewDataBinding>: BaseFragment() {

    protected lateinit var binding: T

    override fun bindWhenOnCreateView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = bindContentView(inflater,container).apply {
            lifecycleOwner = this@BaseViewBindingFragment
        }
        return binding.root
    }

    abstract fun bindContentView(inflater: LayoutInflater, container: ViewGroup?): T

}