package com.mole.android.mole.auth.model

import com.mole.android.mole.component
import com.mole.android.mole.di.FingerprintRepository
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AuthModelImplementation(
    private val service: AuthService,
    private val fingerprintRepository: FingerprintRepository,
    private val mainScope: CoroutineScope
) : AuthModel {

    override suspend fun addUser(login: String): ApiResult<AuthModel.SuccessAuthResult> {
        return withContext(mainScope.coroutineContext) {
            call {
                service.editProfileInfo(login)
                AuthModel.SuccessAuthResult.SuccessEditLogin
            }
        }
    }

    override suspend fun getUserVk(code: String): ApiResult<AuthModel.SuccessAuthResult> {
        return withContext(mainScope.coroutineContext) {
            call {
                val user = service.getVkAuth(code, getFingerprint())
                val accountRepository = component().accountManagerModule.accountRepository
                val success = accountRepository.createAccount(
                    user.accessToken,
                    user.refreshToken
                )
                if (user.login == null) {
                    AuthModel.SuccessAuthResult.SuccessForExistedUser
                } else {
                    AuthModel.SuccessAuthResult.SuccessNewUser(user.login)
                }
            }
        }
    }

    override suspend fun getUserGoogle(code: String): ApiResult<AuthModel.SuccessAuthResult> {
        return withContext(mainScope.coroutineContext + Dispatchers.IO) {
            call {
                val user = service.getGoogleAuth(code, getFingerprint())
                val accountRepository = component().accountManagerModule.accountRepository
                val success = accountRepository.createAccount(
                    user.accessToken,
                    user.refreshToken
                )
                if (user.login == null) {
                    AuthModel.SuccessAuthResult.SuccessForExistedUser
                } else {
                    AuthModel.SuccessAuthResult.SuccessNewUser(user.login)
                }
            }
        }
    }

    private suspend fun getFingerprint(): String {
        return fingerprintRepository.fingerprint.toString()
    }

}
