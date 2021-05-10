package com.zs.base_library.common

import android.content.res.Resources
import java.util.*

/**
 * @author caishuzhan
 */

/**
 * 像素转换
 */
val Number.dp2px get() = (toFloat() * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
val Number.px2dp get() = (toFloat() / Resources.getSystem().displayMetrics.density + 0.5f).toInt()
val Number.px2sp get() = (toFloat() / Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()
val Number.sp2px get() = (toFloat() * Resources.getSystem().displayMetrics.scaledDensity + 0.5f).toInt()


/**
 * 将毫秒转换为分秒-00:00格式
 */
fun stringForTime(timeMs: Int): String {
    val list = mutableListOf<Int>(10, 20)
    list.isNullOrEmpty()

    val totalSeconds = timeMs / 1000
    val seconds = totalSeconds % 60
    val minutes = (totalSeconds / 60) % 60

    return Formatter().format("%02d:%02d", minutes, seconds).toString();
}







