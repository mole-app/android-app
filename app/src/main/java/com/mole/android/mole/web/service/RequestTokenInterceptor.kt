package com.mole.android.mole.web.service

import com.mole.android.mole.BuildConfig
import com.mole.android.mole.di.FingerprintRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write


class RequestTokenInterceptor(
    private val accountRepository: AccountRepository,
    private val fingerprintRepository: FingerprintRepository
) : Interceptor {

    lateinit var refreshService: TokenRefreshService

    companion object {
        const val API_KEY = "ApiKey: true"
        private const val API_KEY_HEADER = "x-api-key"
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val API_KEY_INTERNAL_HEADER = "ApiKey"
        private const val AUTH_HEADER_PREFIX = "Bearer "
        private val tokenUpdateSyncer = ReentrantReadWriteLock()
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
            tokenUpdateSyncer.read {
                val token = accountRepository.accessToken

                nameHeader = AUTHORIZATION_HEADER
                valueHeader = AUTH_HEADER_PREFIX + token
            }
        }

        val request: Request = chain.request().newBuilder()
            .header(nameHeader, valueHeader)
            .removeHeader(API_KEY_INTERNAL_HEADER)
            .build()

        val response = chain.proceed(request)
        if ((response.code() == 401) && !isApiKeyAuth) {
            if (accountRepository.refreshToken != null) {
                tokenUpdateSyncer.write {
                    val refreshToken = accountRepository.refreshToken!!
                    val authTokenData: AuthTokenData
                    runBlocking {
                        authTokenData = refreshService.getNewAuthToken(
                            refreshToken,
                            fingerprintRepository.fingerprint.toString()
                        )
                        accountRepository.accessToken = authTokenData.accessToken
                        accountRepository.refreshToken = authTokenData.refreshToken
                        val updateAuthHeader = AUTH_HEADER_PREFIX + authTokenData.accessToken
                        val requestWithNewToken: Request = chain.request().newBuilder()
                            .header(nameHeader, updateAuthHeader)
                            .build()
                        chain.proceed(requestWithNewToken)
                    }
                }
                val updateAuthHeader: String
                tokenUpdateSyncer.read {
                    updateAuthHeader = AUTH_HEADER_PREFIX + accountRepository.accessToken
                }
                val requestWithNewToken: Request = chain.request().newBuilder()
                    .header(nameHeader, updateAuthHeader)
                    .build()
                return chain.proceed(requestWithNewToken)
            } else {
                accountRepository.removeAccount { }
            }
        }

        return response
    }
}