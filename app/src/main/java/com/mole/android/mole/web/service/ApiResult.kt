package com.mole.android.mole.web.service

import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class ApiResult<T> private constructor(
    private val result: T? = null,
    private val error: MoleError? = null
) {
    fun withResult(action: (T) -> Unit): ApiResult<T> {
        result?.let(action)
        return this
    }

    fun withError(action: (MoleError) -> Unit): ApiResult<T> {
        error?.let {
            if (error.code != HTTP_UNAUTHORIZED && error.code != HTTP_FORBIDDEN) {
                action(it)
            }
        }
        return this
    }

    companion object {
        fun <T> create(result: T) = ApiResult(result = result)
        fun <T> create(error: MoleError) = ApiResult<T>(error = error)
    }

    class MoleError(val code: Int, val description: String)

}
