package com.zs.base_library.common

import java.util.*

/**
 * @author caishuzhan
 */


/**
 * 将毫秒转换为分秒-00:00格式
 */
fun stringForTime(timeMs:Int):String{
    val list = mutableListOf<Int>(10,20)
    list.isNullOrEmpty()

    val totalSeconds = timeMs/1000
    val seconds = totalSeconds % 60
    val minutes = (totalSeconds/60)%60

    return Formatter().format("%02d:%02d",minutes,seconds).toString();
}





