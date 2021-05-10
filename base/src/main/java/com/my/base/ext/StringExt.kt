package com.my.base.ext

import android.annotation.SuppressLint
import android.text.TextUtils
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * 字符串拓展
 * @author caishuzhan
 */


/******************** 正则相关常量 ********************/
/******************** 正则相关常量  */
/**
 * 正则：手机号（简单）
 */
const val REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$"

/**
 * 正则：手机号（精确）
 *
 * 移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188
 *
 * 联通：130、131、132、145、155、156、175、176、185、186
 *
 * 电信：133、153、173、177、180、181、189
 *
 * 全球星：1349
 *
 * 虚拟运营商：170
 */
const val REGEX_MOBILE_EXACT =
    "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$"

/**
 * 正则：电话号码
 */
const val REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}"

/**
 * 正则：身份证号码15位
 */
const val REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$"

/**
 * 正则：身份证号码18位
 */
const val REGEX_ID_CARD18 =
    "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$"

/**
 * 正则：邮箱
 */
const val REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"

/**
 * 正则：URL
 */
const val REGEX_URL = "[a-zA-z]+://[^\\s]*"

/**
 * 正则：汉字
 */
const val REGEX_ZH = "^[\\u4e00-\\u9fa5]+$"

/**
 * 正则：用户名，取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
 */
const val REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$"

/**
 * 正则：yyyy-MM-dd格式的日期校验，已考虑平闰年
 */
const val REGEX_DATE =
    "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$"

/**
 * 正则：IP地址
 */
const val REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)"

/************** 以下摘自http://tool.oschina.net/regex **************/
/************** 以下摘自http://tool.oschina.net/regex  */
/**
 * 正则：双字节字符(包括汉字在内)
 */
const val REGEX_DOUBLE_BYTE_CHAR = "[^\\x00-\\xff]"

/**
 * 正则：空白行
 */
const val REGEX_BLANK_LINE = "\\n\\s*\\r"

/**
 * 正则：QQ号
 */
const val REGEX_TENCENT_NUM = "[1-9][0-9]{4,}"

/**
 * 正则：中国邮政编码
 */
const val REGEX_ZIP_CODE = "[1-9]\\d{5}(?!\\d)"

/**
 * 正则：正整数
 */
const val REGEX_POSITIVE_INTEGER = "^[1-9]\\d*$"

/**
 * 正则：负整数
 */
const val REGEX_NEGATIVE_INTEGER = "^-[1-9]\\d*$"

/**
 * 正则：整数
 */
const val REGEX_INTEGER = "^-?[1-9]\\d*$"

/**
 * 正则：非负整数(正整数 + 0)
 */
const val REGEX_NOT_NEGATIVE_INTEGER = "^[1-9]\\d*|0$"

/**
 * 正则：非正整数（负整数 + 0）
 */
const val REGEX_NOT_POSITIVE_INTEGER = "^-[1-9]\\d*|0$"

/**
 * 正则：正浮点数
 */
const val REGEX_POSITIVE_FLOAT = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$"

/**
 * 正则：负浮点数
 */
const val REGEX_NEGATIVE_FLOAT = "^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$"

//    public static final String REGEX_NUM_LETTER= "^[A-Za-z0-9]+$";
const val REGEX_NUM_LETTER = "^[0-9a-zA-Z]+$"

/**
 * 正则：浮点数保留两位小数
 */
const val REGEX_TOW_DOUBLE = "^\\d+\\.?\\d{0,2}$"

/************** If u want more please visit http://toutiao.com/i6231678548520731137/ **************/


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

fun String.checkMobilePhone(): Boolean {
    val pattern = Pattern.compile(REGEX_MOBILE_SIMPLE)
    return pattern.matcher(this).matches()
}

fun String.checkEmail(): Boolean {
    val pattern = Pattern.compile(REGEX_EMAIL)
    return pattern.matcher(this).matches()
}

fun String.checkUserName(): Boolean {
    val pattern = Pattern.compile(REGEX_USERNAME)
    return pattern.matcher(this).matches()
}

fun String.checkMechanismCode(): Boolean {
    val pattern = Pattern.compile("^[a-zA-Z].*$")
    return pattern.matcher(this).matches()
}

fun String.checkEnglish(): Boolean {
    val pattern = Pattern.compile("^[(.#_/*<>$!~)&%!@a-zA-Z0-9].*$")
    return pattern.matcher(this).matches()
}

fun String.isWebUrl(): Boolean {
    return this.startsWith("http://") || this.startsWith("https://")
}

fun String.hidePhone(): String =
    if (length == 11) "${take(3)}****${takeLast(4)}" else this

fun String.orEmptyHolder(holder: String): String =
    if (TextUtils.isEmpty(this)) holder else this

/**
 * 银行卡以[*******1234]的方式显示
 */
fun String.toBandCard(): String {
    val length = this.length
    val stringBuffer = StringBuffer()
    for (i in 0 until length - 4) {
        stringBuffer.append("*")
    }
    val str = stringBuffer.toString().trim { it <= ' ' }
        .replace("(.{4})".toRegex(), "$1 ")
    return str + " " + this.substring(length - 4, length)
}

/**
 * 用户名[135****1234]的方式显示
 */
fun String.toUserAccount(): String {
    val length = this.length
    if (length <= 3) {
        return this
    }
    return if (this.length > 7) {
        take(3) + "****" + takeLast(4)
    } else {
        take(3) + "****"
    }
}

