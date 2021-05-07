package com.my.base.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

const val TIME_PATTERN_1 = "yyyy-MM-dd HH:mm:ss"
const val TIME_PATTERN_2 = "yyyy-MM-dd HH:mm"
const val TIME_PATTERN_3 = "yyyy-MM-dd"
const val TIME_PATTERN_4 = "yyyy-MM"
const val TIME_PATTERN_5 = "MM-dd HH:mm"
const val TIME_PATTERN_6 = "MM-dd"
const val TIME_PATTERN_7 = "HH:mm"

@SuppressLint("SimpleDateFormat")
fun Long.formatTime(timePattern: String): String =
    SimpleDateFormat(timePattern).format(Date(this))

@SuppressLint("SimpleDateFormat")
fun String.parseTimeStamp(timeStamp: String, timePattern: String): Date =
        SimpleDateFormat(timePattern).parse(timeStamp)

