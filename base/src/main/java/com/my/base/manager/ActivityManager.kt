package com.my.base.manager

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ListView
import java.util.*

/**
 * Activity管理器
 * @author caishuzhan
 */
object ActivityManager {

    private val activityStack: Stack<Activity> by lazy {
        Stack()
    }

    /**
     * add Activity to Stack
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /**
     * remove Activity from Stack
     */
    fun removeActivity(activity: Activity) {
        activityStack.remove(activity)
    }

    /**
     * get current activity from Stack
     */
    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    fun finishActivity() {
        val activity = activityStack.lastElement()
        finishActivity(activity)
    }

    fun finishActivity(activity: Activity?) {
        activity?.let {
            activityStack.remove(activity)
            activity.finish()
        }
    }

    fun finishActivity(cls: Class<out Activity>) {
        for (activity in activityStack) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
            }
        }
    }

    fun finishAllActivity() {
        for (activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
        //杀死该应用进程
//		android.os.Process.killProcess(android.os.Process.myPid());
    }

    fun unbindReferences(view: View) {
        try {
            view.destroyDrawingCache()
            unbindViewReferences(view)
            if (view is ViewGroup) {
                unbindViewGroupReferences(view)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun unbindViewGroupReferences(viewGroup: ViewGroup) {
        val nrOfChildren = viewGroup.childCount
        for (i in 0 until nrOfChildren) {
            val view = viewGroup.getChildAt(i)
            unbindViewReferences(view)
            if (view is ViewGroup) unbindViewGroupReferences(view)
        }
        try {
            viewGroup.removeAllViews()
        } catch (mayHappen: Throwable) {
            mayHappen.printStackTrace()
        }
    }

    private fun unbindViewReferences(view: View) {
        try {
            view.setOnClickListener(null)
            view.setOnCreateContextMenuListener(null)
            view.onFocusChangeListener = null
            view.setOnKeyListener(null)
            view.setOnLongClickListener(null)
            view.setOnClickListener(null)
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        // set background to null
        var d = view.background
        if (d != null) {
            d.callback = null
        }
        if (view is ImageView) {
            val imageView = view
            d = imageView.drawable
            if (d != null) {
                d.callback = null
            }
            imageView.setImageDrawable(null)
            imageView.setBackgroundDrawable(null)
        }

        // destroy WebView
        if (view is WebView) {
            var webview: WebView? = view
            webview!!.stopLoading()
            webview.clearFormData()
            webview.clearDisappearingChildren()
            webview.webChromeClient = null
            webview.destroyDrawingCache()
            webview.destroy()
            webview = null
        }
        if (view is ListView) {
            try {
                view.removeAllViewsInLayout()
            } catch (mayHappen: Throwable) {
                mayHappen.printStackTrace()
            }
            view.destroyDrawingCache()
        }
    }

    /**
     * exit System
     *
     * @param context
     * @param isClearCache
     */
    fun exit() {
        try {
            finishAllActivity()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}