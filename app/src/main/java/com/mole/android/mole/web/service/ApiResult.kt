package com.mole.android.mole.web.service

class ApiResult<T> private constructor(
    private val result: T? = null,
    private val error: MoleError? = null
) {
    fun withResult(action: (T) -> Unit): ApiResult<T> {
        result?.let(action)
        return this
    }

    fun withError(action: (MoleError) -> Unit): ApiResult<T> {
        error?.let(action)
        return this
    }

    companion object {
        fun <T> create(result: T) = ApiResult(result = result)
        fun <T> create(error: MoleError) = ApiResult<T>(error = error)
    }

    class MoleError(val code: Int)

}
