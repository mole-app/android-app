package com.mole.android.mole.web.service

import com.mole.android.mole.BuildConfig
import com.mole.android.mole.component
import com.mole.android.mole.di.AccountManagerModule.Companion.ACCESS_TOKEN
import com.mole.android.mole.di.AccountManagerModule.Companion.REFRESH_TOKEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class RequestTokenInterceptor : Interceptor {

    lateinit var refreshService: TokenRefreshService

    companion object {
        const val API_KEY = "ApiKey: true"
        const val API_KEY_HEADER = "x-api-key"
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val API_KEY_INTERNAL_HEADER = "ApiKey"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val header = chain.request().header(API_KEY_INTERNAL_HEADER)
        val isApiKeyAuth = header != null

        val nameHeader: String
        val valueHeader: String
        if (isApiKeyAuth) {
            nameHeader = API_KEY_HEADER
            valueHeader = BuildConfig.X_API_KEY
        } else {
            val accountModule = component().accountManagerModule
            val token = accountModule.accessToken

            nameHeader = AUTHORIZATION_HEADER
            valueHeader = "Bearer $token"
        }

        val request: Request = chain.request().newBuilder()
            .header(nameHeader, valueHeader)
            .removeHeader(API_KEY_INTERNAL_HEADER)
            .build()

        // Here where we'll try to refresh token.
        // with an retrofit call
        // After we succeed we'll proceed our request

        val response = chain.proceed(request)
        if ((response.code() == 401) && !isApiKeyAuth) {
            val accountModule = component().accountManagerModule
            val refreshToken = accountModule.refreshToken!!
            val profileId = accountModule.profileId!!
            val authTokenData = refreshService.getNewAuthToken(profileId.toLong(), refreshToken, component().firebaseModule.fingerprint.toString())
            accountModule.accessToken = authTokenData.accessToken
            accountModule.refreshToken = authTokenData.refreshToken
        }

        return response
    }
}