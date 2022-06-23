package com.mole.android.mole.web.service

import retrofit2.HttpException
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

fun Exception.asMoleError() = when(this) {
    is HttpException -> ApiResult.MoleError(code(), message())
    else -> ApiResult.MoleError(-1, "Unknown error: ${this.message}")
}

inline fun <T> call(call: () -> T): ApiResult<T> {
    return try {
        val result = call()
        ApiResult.create(result)
    } catch (exception: Exception) {
        ApiResult.create(exception.asMoleError())
    }
}
