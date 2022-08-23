package com.mole.android.mole.auth.model

import com.mole.android.mole.web.service.ApiResult

interface AuthModel {
    suspend fun addUser(login: String): ApiResult<SuccessAuthResult>

    suspend fun getUserVk(code: String): ApiResult<SuccessAuthResult>

    suspend fun getUserGoogle(code: String): ApiResult<SuccessAuthResult>

    sealed class SuccessAuthResult {
        object SuccessForExistedUser : SuccessAuthResult()
        class SuccessNewUser(val login: String) : SuccessAuthResult()
        object SuccessEditLogin : SuccessAuthResult()
    }
}