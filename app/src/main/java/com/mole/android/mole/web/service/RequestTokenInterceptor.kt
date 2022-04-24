package com.mole.android.mole.web.service

import com.google.gson.Gson
import com.mole.android.mole.BuildConfig
import com.mole.android.mole.component
import com.mole.android.mole.di.FingerprintRepository
import kotlinx.coroutines.runBlocking
import okhttp3.*
import java.net.HttpURLConnection
import java.net.HttpURLConnection.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write


class RequestTokenInterceptor(
    private val accountRepository: AccountRepository,
    private val fingerprintRepository: FingerprintRepository
) : Interceptor {

    companion object {
        const val API_KEY = "ApiKey: true"
        private const val API_KEY_HEADER = "x-api-key"
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val API_KEY_INTERNAL_HEADER = "ApiKey"
        private const val AUTH_HEADER_PREFIX = "Bearer "
        private const val SCHEME = "https"
        private const val HOST = "mole-app-dev.ru"
        private const val PORT = 8443
        private const val REFRESH_TOKEN_QUERY = "refreshToken"
        private const val FINGERPRINT_TOKEN_QUERY = "fingerprint"
        private const val UPDATE_TOKEN_URL = "api/auth/refreshToken"
        private val okHttpClient: OkHttpClient = OkHttpClient()
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
        if ((response.code() == HTTP_UNAUTHORIZED) && !isApiKeyAuth) {
            val accountRepository = component().accountManagerModule.accountRepository
            if (accountRepository.refreshToken != null) {
                tokenUpdateSyncer.write {
                    val refreshToken = accountRepository.refreshToken
                    if (refreshToken != null) {
                        val authTokenData: AuthTokenData?
                        runBlocking {
                            val authTokenDataUrl: HttpUrl = HttpUrl.Builder()
                                .scheme(SCHEME)
                                .host(HOST)
                                .port(PORT)
                                .addPathSegments(UPDATE_TOKEN_URL)
                                .addQueryParameter(REFRESH_TOKEN_QUERY, refreshToken)
                                .addQueryParameter(
                                    FINGERPRINT_TOKEN_QUERY,
                                    component().firebaseModule.fingerprint.toString()
                                )
                                .build()

                            val authTokenDataRequest =
                                Request.Builder().url(authTokenDataUrl)
                                    .header(API_KEY_HEADER, BuildConfig.X_API_KEY)
                                    .build()
                            val authTokenDataResponse =
                                okHttpClient.newCall(authTokenDataRequest).execute()

                            when (authTokenDataResponse.code()) {
                                HTTP_OK -> {
                                    authTokenData = Gson().fromJson(
                                        authTokenDataResponse.body()?.string(),
                                        AuthTokenData::class.java
                                    )
                                    accountRepository.accessToken = authTokenData.accessToken
                                    accountRepository.refreshToken = authTokenData.refreshToken
                                }
                                HTTP_FORBIDDEN, HTTP_UNAUTHORIZED -> {
                                    // HTTP_UNAUTHORIZED не должен происходить, но кажется логичным в таком случае делать логаут
                                    authTokenData = null
                                    accountRepository.removeAccount { }
                                }
                                else -> authTokenData = null
                            }
                        }
                    } else {
                        accountRepository.removeAccount { }
                        response
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