package com.mole.android.mole.auth.model

import android.util.Log
import com.google.firebase.installations.FirebaseInstallations
import com.mole.android.mole.auth.data.AuthDataVkLogin
import com.mole.android.mole.component
import kotlinx.coroutines.*

import android.accounts.Account
import com.mole.android.mole.di.FirebaseModule


class AuthModelImplementation(
    private val service: AuthService,
    private val firebaseInst: FirebaseModule,
    private val mainScope: CoroutineScope
) : AuthModel {

    private val data: AuthDataVkLogin =
        AuthDataVkLogin(
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
        val task = mainScope.async {
            val user: AuthDataVkLogin
            withContext(Dispatchers.IO) {
                user = try {
                    val user = service.getVkAuth(code, getFingerprint())
                    val accountManager = component().accountManager
                    val account = Account("VovchikPut", "com.mole.android.mole")
                    val success = accountManager.addAccountExplicitly(account, null, null)
                    accountManager.setAuthToken(account, "accessAuthToken", user.accessToken)
                    accountManager.setAuthToken(account, "refreshAuthToken", user.refreshToken)
                    val profileId = service.getProfileInfo().profile.id
                    Log.i("ProfileId", "Profile id $profileId")
                    accountManager.setUserData(account, "profileId", profileId.toString())
                    user
                } catch (exception: Exception) {
                    // Не хочется падать если что-то не так на сервере
                    exception.printStackTrace()
                    data
                }
                user
            }
        }
        val user: AuthDataVkLogin = task.await()
        Log.i("Auth", "User vk login: $user")
        return user.login
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
        Log.i("Auth", "User google login: $login")
        return login
    }

    private suspend fun getFingerprint(): String {
        return firebaseInst.fingerprint.toString()
    }

}
