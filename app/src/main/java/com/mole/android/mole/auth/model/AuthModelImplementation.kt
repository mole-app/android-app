package com.mole.android.mole.auth.model

import android.util.Log
import android.widget.Toast
import com.google.firebase.installations.FirebaseInstallations
import com.mole.android.mole.auth.data.AuthDataVkLogin
import kotlinx.coroutines.*

class AuthModelImplementation(
    private val service: AuthService,
    private val firebaseInst: FirebaseInstallations,
    private val mainScope: CoroutineScope
) : AuthModel {

    private val data: AuthDataVkLogin =
        AuthDataVkLogin(
            "accessToken",
            "refreshToken",
            "expiresIn",
            "vasiapupkin"
        )

    override suspend fun addUser(login: String): Boolean {
        return login != "first"
    }

    override suspend fun getUserVk(code: String): String {
        val task = mainScope.async {
            val login: String
            withContext(Dispatchers.IO) {
                login = try {
                    service.getVkAuth(code, getFingerprint()).login
                } catch (exception: Exception) {
                    // Не хочется падать если что-то не так на сервере
                    exception.printStackTrace()
                    ""
                }
                login
            }
        }
        val login: String = task.await()
        Log.i("Auth", "User login: $login")
        return login
    }

    override suspend fun getUserGoogle(code: String): String {
        val task = mainScope.async {
            val login: String
            withContext(Dispatchers.IO) {
                login = try {
                    service.getGoogleAuth(code, getFingerprint()).login
                } catch (exception: Exception) {
                    // Не хочется падать если что-то не так на сервере
                    exception.printStackTrace()
                    ""
                }
                login
            }
        }
        val login: String = task.await()
        Log.i("Auth", "User login: $login")
        return login
    }

    private suspend fun getFingerprint(): String {
        firebaseInst.id
        return ""
    }

}
