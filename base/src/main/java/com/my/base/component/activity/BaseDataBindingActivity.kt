package com.my.base.component.activity

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * DataBinding的封装
 * @author caishuzhan
 */
abstract class BaseDataBindingActivity<T : ViewDataBinding> : BaseActivity() {

    protected lateinit var binding: T

    override fun bindWhenOnCreate(): View {
        binding = bindContentView(getLayoutId())
        return binding.root
    }

    fun bindContentView(layoutId: Int): T =
        DataBindingUtil.setContentView<T>(this, layoutId)
            .apply {
                lifecycleOwner = this@BaseDataBindingActivity
            }

    abstract fun getLayoutId(): Int

}