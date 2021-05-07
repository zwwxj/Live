package com.my.base.component.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.csz.permission.core.PermissionHelper
import com.gyf.immersionbar.ImmersionBar
import com.my.base.helper.EventBusHepler
import com.my.base.manager.ActivityManager
import com.my.base.util.InputMethodUtils

/**
 * @author caishuzhan
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerEventBus()
        onHandleIntent(intent)
        onImmersionBarConfig()
        ActivityManager.addActivity(this)

        initContent(bindWhenOnCreate(), savedInstanceState)
    }

    protected open fun onHandleIntent(intent: Intent) {}

    protected open fun onImmersionBarConfig() {
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).keyboardEnable(true)
            .init()
    }

    abstract fun bindWhenOnCreate(): View

    protected fun initContent(view: View, savedInstanceState: Bundle?) {
        initUI(savedInstanceState)
        InputMethodUtils.observeKeyBoard(view) {
            onKeyboardChange(it)
        }
    }

    override fun onPause() {
        super.onPause()
        InputMethodUtils.hideSoftKeyboard(this)
    }

    override fun onStop() {
        super.onStop()
        if (isFinishing) {
            ActivityManager.removeActivity(this)
            onRelease()
            EventBusHepler.unRegister(this)
        }
    }

    //Activity销毁时候资源释放
    protected open fun onRelease() {}

    protected open fun initUI(savedInstanceState: Bundle?) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionHelper.onReqPermissionsResult(requestCode, permissions, grantResults)
        dispatchPermissionsResult(requestCode, permissions, grantResults)
    }

    fun dispatchPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        val manager = this.supportFragmentManager
        val list = manager.fragments
        if (!list.isEmpty()) {
            for (fragment in list) {
                if (fragment != null && fragment.isVisible) {
                    fragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
                }
            }
        }
    }

    /**
     * 软键盘可见改变
     */
    protected open fun onKeyboardChange(visible: Int) {}

    private fun registerEventBus() {
        if (isEventBusEnable()) {
            EventBusHepler.register(this)
        }
    }

    /**
     * EventBus开关
     */
    protected open fun isEventBusEnable(): Boolean {
        return false
    }

}