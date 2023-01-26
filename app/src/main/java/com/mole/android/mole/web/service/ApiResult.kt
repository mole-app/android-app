package com.mole.android.mole.web.service

import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class ApiResult<T> private constructor(
    val result: T? = null,
    val error: MoleError? = null
) {
    inline fun withResult(action: (T) -> Unit): ApiResult<T> {
        result?.let(action)
        return this
    }

    inline fun withError(action: (MoleError) -> Unit): ApiResult<T> {
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
}

inline fun <T> call(call: () -> T): ApiResult<T> {
    return try {
        val result = call()
        ApiResult.create(result)
    } catch (exception: Exception) {
        ApiResult.create(exception.asMoleError())
    }
}
