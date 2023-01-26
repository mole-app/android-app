package com.mole.android.mole.web.service

import retrofit2.HttpException

class MoleError(val code: Int, val description: String)

fun Exception.asMoleError() = when (this) {
    is HttpException -> MoleError(code(), message())
    else -> MoleError(-1, "Unknown error: ${this.message}")
}
