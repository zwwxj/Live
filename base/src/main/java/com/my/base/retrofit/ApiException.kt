package com.my.base.retrofit

import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

/**
 * @author caishuzhan
 */
class ApiException private constructor(val errorMessage: String, val errorCode: Int) : Throwable() {

    companion object{
        fun getApiException(e: Throwable): ApiException {
            return when (e) {
                is UnknownHostException -> {
                    ApiException("网络异常", -100)
                }
                is JSONException -> {
                    ApiException("数据异常", -100)
                }
                is JsonParseException -> {
                    ApiException("json解析出错", -100)
                }
                is SocketTimeoutException -> {
                    ApiException("连接超时", -100)
                }
                is ConnectException -> {
                    ApiException("连接错误", -100)
                }
                is HttpException -> {
                    ApiException("http code ${e.code()}", -100)
                }
                is ApiException -> {
                    e
                }
                is CancellationException -> {
                    ApiException("", -10)
                }
                else -> {
                    ApiException("未知错误", -100)
                }
            }
        }
    }
}
