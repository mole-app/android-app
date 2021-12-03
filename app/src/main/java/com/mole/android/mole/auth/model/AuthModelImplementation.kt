package com.mole.android.mole.auth.model

import android.util.Log
import com.mole.android.mole.auth.data.AuthDataLogin
import com.mole.android.mole.component
import kotlinx.coroutines.*

import com.mole.android.mole.di.FirebaseModule


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
            "",
            ""
        )

    override suspend fun addUser(login: String): Boolean {
        return login != "first"
    }

    override suspend fun getUserVk(code: String): String {
        val task = mainScope.async(Dispatchers.IO) {
            try {
                val user = service.getVkAuth(code, getFingerprint())
                val accountRepository = component().accountManagerModule.accountRepository
                val success = accountRepository.createAccount(
                    user.login ?: "VovchikPut",
                    user.accessToken,
                    user.refreshToken
                )
                user
            } catch (exception: Exception) {
                // Не хочется падать если что-то не так на сервере
                exception.printStackTrace()
                data
            }
        }
        val user: AuthDataLogin = task.await()
        Log.i("Auth", "User vk login: $user")
        return user.login
    }

    override suspend fun getUserGoogle(code: String): String {
        val task = mainScope.async(Dispatchers.IO) {
            try {
                val user = service.getGoogleAuth(code, getFingerprint())
                val accountRepository = component().accountManagerModule.accountRepository
                val success = accountRepository.createAccount(
                    user.login ?: "VovchikPut",
                    user.accessToken,
                    user.refreshToken
                )
                user
            } catch (exception: Exception) {
                // Не хочется падать если что-то не так на сервере
                exception.printStackTrace()
                data
            }
        }
        val user = task.await()
        Log.i("Auth", "User google login: ${user.login}")
        return user.login
    }

    private suspend fun getFingerprint(): String {
        return firebaseInst.fingerprint.toString()
    }

}
