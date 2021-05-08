package com.my.base.retrofit


/**
 * @author caishuzhan
 */
data class ResultData<T>(val requestStatus: RequestStatus,
                         val data: T?,
                         val isCache: Boolean = false,
                         val error: ApiException? = null,
                         val tag: Any? = null) {
    companion object {

        fun <T> start(): ResultData<T> {
            return ResultData(
                RequestStatus.START,
                null
            )
        }

        fun <T> success(data: T?, isCache: Boolean = false): ResultData<T> {
            return ResultData(
                RequestStatus.SUCCESS,
                data,
                isCache
            )
        }

        fun <T> error(error: ApiException?): ResultData<T> {
            return ResultData(
                RequestStatus.ERROR,
                null,
                false,
                error
            )
        }

    }
}

