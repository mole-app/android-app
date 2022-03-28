package com.mole.android.mole.auth.model

import com.mole.android.mole.auth.data.AuthDataLogin
import com.mole.android.mole.component
import kotlinx.coroutines.*

import com.mole.android.mole.di.FirebaseModule
import com.mole.android.mole.web.service.ApiResult
import retrofit2.HttpException


class AuthModelImplementation(
    private val service: AuthService,
    private val firebaseInst: FirebaseModule,
    private val mainScope: CoroutineScope
) : AuthModel {

    private val data: AuthDataLogin =
        AuthDataLogin(
            "",
            "",
            "",
            ""
        )

    override suspend fun addUser(login: String): Boolean {
        return login != "first"
    }

    override suspend fun getUserVk(code: String): ApiResult<AuthModel.SuccessAuthResult> {
        val task = mainScope.async(Dispatchers.IO) {
            try {
                val user = service.getVkAuth(code, getFingerprint())
                if (user.login == null) {
                    ApiResult.create<AuthModel.SuccessAuthResult>(AuthModel.SuccessAuthResult.SuccessForExistedUser)
                } else {
                    val accountRepository = component().accountManagerModule.accountRepository
                    val success = accountRepository.createAccount(
                        user.login,
                        user.accessToken,
                        user.refreshToken
                    )
                    ApiResult.create<AuthModel.SuccessAuthResult>(
                        AuthModel.SuccessAuthResult.SuccessNewUser(
                            user.login
                        )
                    )
                }
            } catch (exception: HttpException) {
                // Не хочется падать если что-то не так на сервере
                ApiResult.create(ApiResult.MoleError(exception.code(), exception.message()))
            }
        }
        return task.await()
    }

    override suspend fun getUserGoogle(code: String): ApiResult<AuthModel.SuccessAuthResult> {
        val task = mainScope.async(Dispatchers.IO) {
            try {
                val user = service.getGoogleAuth(code, getFingerprint())
                if (user.login == null) {
                    ApiResult.create<AuthModel.SuccessAuthResult>(AuthModel.SuccessAuthResult.SuccessForExistedUser)
                } else {
                    val accountRepository = component().accountManagerModule.accountRepository
                    val success = accountRepository.createAccount(
                        user.login,
                        user.accessToken,
                        user.refreshToken
                    )
                    ApiResult.create<AuthModel.SuccessAuthResult>(
                        AuthModel.SuccessAuthResult.SuccessNewUser(
                            user.login
                        )
                    )
                }
            } catch (exception: HttpException) {
                // Не хочется падать если что-то не так на сервере
                ApiResult.create(ApiResult.MoleError(exception.code(), exception.message()))
            }
        }
        return task.await()
    }

    private suspend fun getFingerprint(): String {
        return firebaseInst.fingerprint.toString()
    }

}
