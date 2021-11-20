package com.mole.android.mole.web.service

import com.mole.android.mole.component
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class RequestTokenInterceptor : Interceptor {

    lateinit var refreshService: TokenRefreshService

    override fun intercept(chain: Interceptor.Chain): Response {

        val header = chain.request().header("ApiKey")
        val isApiKeyAuth = header != null

        val nameHeader: String
        val valueHeader: String
        if (isApiKeyAuth) {
            nameHeader = "x-api-key"
            valueHeader = "testAndroid"
        } else {
            val accountManager = component().accountManager
            val accounts = accountManager.getAccountsByType("com.mole.android.mole")
            val token = if (accounts.isNotEmpty()) {
                accountManager.peekAuthToken(accounts[0], "accessAuthToken")
            } else {
                null
            }

            nameHeader = "Authorization"
            valueHeader = "Bearer $token"
        }

        val request: Request = chain.request().newBuilder()
            .header(nameHeader, valueHeader)
            .removeHeader("ApiKey")
            .build()

        // Here where we'll try to refresh token.
        // with an retrofit call
        // After we succeed we'll proceed our request

        val response = chain.proceed(request)
        if ((response.code() == 401) && !isApiKeyAuth) {
            val accountManager = component().accountManager
            val accounts = accountManager.getAccountsByType("com.mole.android.mole")
            val account = accounts[0]
            val refreshToken = accountManager.peekAuthToken(account, "refreshAuthToken")
            val profileId = accountManager.getUserData(account, "profileId")
            val authTokenData = refreshService.getNewAuthToken(profileId.toLong(), refreshToken, component().firebaseModule.fingerprint.toString())
            accountManager.setAuthToken(account, "accessAuthToken", authTokenData.accessToken)
            accountManager.setAuthToken(account, "refreshAuthToken", authTokenData.refreshToken)
        }

        return response
    }
}