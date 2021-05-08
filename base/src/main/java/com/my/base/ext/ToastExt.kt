package com.zs.base_library.common

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.StringRes
import com.my.base.util.ContextUtils
import com.my.base.util.ToastUtils

/**
 *
 * @author caishuzhan
 */

fun Context.toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    if (TextUtils.isEmpty(content))return
    ToastUtils.show(content, Gravity.CENTER,duration)
}

fun Context.toast(@StringRes id: Int, duration: Int = Toast.LENGTH_SHORT) {
    toast(getString(id), duration)
}

fun toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
    if (TextUtils.isEmpty(content))return
    ContextUtils.getContext().toast(content, duration)
}

fun toast(@StringRes id: Int, duration: Int= Toast.LENGTH_SHORT) {
    ContextUtils.getContext().toast(id, duration)
}

fun toastLong(content: String,duration: Int= Toast.LENGTH_LONG) {
    if (TextUtils.isEmpty(content))return
    ContextUtils.getContext().toast(content,duration)
}

fun toastLong(@StringRes id: Int,duration: Int= Toast.LENGTH_LONG) {
    ContextUtils.getContext().toast(id,duration)
}


