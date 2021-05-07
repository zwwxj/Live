package com.my.base.component.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.my.base.helper.EventBusHepler

/**
 * @author caishuzhan
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        registerEventBus()
        arguments?.let { onHandleArgs(it) }

        var content: View
        if (view == null) {
            content = bindWhenOnCreateView(inflater,container)
        } else {
            content = requireView()
            val parent = content.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(content)
            }
        }
        initUI(
            inflater,
            container,
            savedInstanceState
        )
        return content
    }

    protected open fun onHandleArgs(arguments: Bundle) {}

    override fun onDestroy() {
        super.onDestroy()
        EventBusHepler.unRegister(this)
    }

    abstract fun bindWhenOnCreateView(inflater: LayoutInflater, container: ViewGroup?): View

    protected open fun initUI(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) {
    }

    protected fun registerEventBus() {
        if (isEventBusEnable()) {
            EventBusHepler.register(this)
        }
    }

    /**
     * 默认关闭EventBus,需要可以重写
     */
    protected fun isEventBusEnable(): Boolean {
        return false
    }
}