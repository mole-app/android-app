package com.mole.android.mole.web.service

import com.mole.android.mole.BuildConfig
import com.mole.android.mole.component
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException


class RequestTokenInterceptor : Interceptor {

    lateinit var refreshService: TokenRefreshService

    companion object {
        const val API_KEY = "ApiKey: true"
        private const val API_KEY_HEADER = "x-api-key"
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val API_KEY_INTERNAL_HEADER = "ApiKey"
        private const val SYNC_OBJECT = "Sync"
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
            val accountRepository = component().accountManagerModule.accountRepository
            val token = accountRepository.accessToken

            nameHeader = AUTHORIZATION_HEADER
            valueHeader = "Bearer $token"
        }

        val request: Request = chain.request().newBuilder()
            .header(nameHeader, valueHeader)
            .removeHeader(API_KEY_INTERNAL_HEADER)
            .build()

        val response = chain.proceed(request)
        if ((response.code() == 401) && !isApiKeyAuth) {
            val accountRepository = component().accountManagerModule.accountRepository
            if (accountRepository.refreshToken != null) {
                return synchronized(SYNC_OBJECT) {
                    val refreshToken = accountRepository.refreshToken!!
                    val authTokenData: AuthTokenData
                    runBlocking {
                        authTokenData = refreshService.getNewAuthToken(
                            refreshToken,
                            component().firebaseModule.fingerprint.toString()
                        )
                        accountRepository.accessToken = authTokenData.accessToken
                        accountRepository.refreshToken = authTokenData.refreshToken
                        val updateAuthHeader = "Bearer ${authTokenData.accessToken}"
                        val requestWithNewToken: Request = chain.request().newBuilder()
                            .header(nameHeader, updateAuthHeader)
                            .build()
                        chain.proceed(requestWithNewToken)
                    }
                    val updateAuthHeader = "Bearer ${authTokenData.accessToken}"
                    val requestWithNewToken: Request = chain.request().newBuilder()
                        .header(nameHeader, updateAuthHeader)
                        .build()
                    chain.proceed(requestWithNewToken)
                }
            } else {
                accountRepository.removeAccount { }
            }
        }

        return response
    }
}