package com.mole.android.mole.web.service

import com.google.gson.Gson
import com.mole.android.mole.BuildConfig
import com.mole.android.mole.component
import kotlinx.coroutines.runBlocking
import okhttp3.*
import java.net.HttpURLConnection.*
import java.util.concurrent.TimeUnit


class RequestTokenInterceptor(
    chuckerInterceptor: Interceptor
) : Interceptor {

    companion object {
        const val API_KEY = "ApiKey: true"
        private const val API_KEY_HEADER = "x-api-key"
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val API_KEY_INTERNAL_HEADER = "ApiKey"
        private const val SYNC_OBJECT = "Sync"
        private const val AUTH_HEADER_PREFIX = "Bearer "
        private const val SCHEME = "https"
        private const val HOST = "mole-app-dev.ru"
        private const val PORT = 8443
        private const val REFRESH_TOKEN_QUERY = "refreshToken"
        private const val FINGERPRINT_TOKEN_QUERY = "fingerprint"
        private const val UPDATE_TOKEN_URL = "api/auth/refreshToken"
    }

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(chuckerInterceptor)
        .build()

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
            valueHeader = AUTH_HEADER_PREFIX + token
        }

        val request: Request = chain.request().newBuilder()
            .header(nameHeader, valueHeader)
            .removeHeader(API_KEY_INTERNAL_HEADER)
            .build()

        val response = chain.proceed(request)
        if ((response.code() == HTTP_UNAUTHORIZED) && !isApiKeyAuth) {
            val accountRepository = component().accountManagerModule.accountRepository
            if (accountRepository.refreshToken != null) {
                return synchronized(SYNC_OBJECT) {
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
                                .addQueryParameter(FINGERPRINT_TOKEN_QUERY, component().firebaseModule.fingerprint.toString())
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
                        if (authTokenData != null) {
                            val updateAuthHeader = AUTH_HEADER_PREFIX + authTokenData.accessToken
                            val requestWithNewToken: Request = chain.request().newBuilder()
                                .header(nameHeader, updateAuthHeader)
                                .build()
                            chain.proceed(requestWithNewToken)
                        } else {
                            response
                        }
                    } else {
                        accountRepository.removeAccount { }
                        response
                    }
                }
            } else {
                accountRepository.removeAccount { }
            }
        }

        return response
    }
}