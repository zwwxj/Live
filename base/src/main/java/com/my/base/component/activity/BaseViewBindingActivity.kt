package com.my.base.component.activity

import android.view.View
import androidx.databinding.ViewDataBinding

/**
 * ViewBinding的封装
 * @author caishuzhan
 */
abstract class BaseViewBindingActivity<T : ViewDataBinding> : BaseActivity() {

    protected lateinit var binding: T

    override fun bindWhenOnCreate(): View {
        binding = bindContentView().apply {
            lifecycleOwner = this@BaseViewBindingActivity
        }
        setContentView(binding.root)
        return binding.root
    }

    abstract fun bindContentView(): T

}