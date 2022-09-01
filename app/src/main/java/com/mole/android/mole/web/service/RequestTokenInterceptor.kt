package com.mole.android.mole.web.service

import com.google.gson.Gson
import com.mole.android.mole.component
import com.mole.android.mole.di.FingerprintRepository
import com.mole.android.mole.web.service.RetrofitBuilder.API_PATH
import com.mole.android.mole.web.service.RetrofitBuilder.HOST
import com.mole.android.mole.web.service.RetrofitBuilder.PORT
import com.mole.android.mole.web.service.RetrofitBuilder.SCHEME
import kotlinx.coroutines.runBlocking
import okhttp3.*
import java.net.HttpURLConnection.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write


class RequestTokenInterceptor(
    chuckerInterceptor: Interceptor,
    private val accountRepository: AccountRepository,
    private val fingerprintRepository: FingerprintRepository,
    private val apiKey: String,
) : Interceptor {

    companion object {
        const val API_KEY = "ApiKey: true"
        private const val API_KEY_HEADER = "x-api-key"
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val API_KEY_INTERNAL_HEADER = "ApiKey"
        private const val AUTH_HEADER_PREFIX = "Bearer "
        private const val REFRESH_TOKEN_QUERY = "refreshToken"
        private const val FINGERPRINT_TOKEN_QUERY = "fingerprint"
        private const val UPDATE_TOKEN_URL = "$API_PATH/auth/refreshToken"
        private val tokenUpdateSyncer = ReentrantReadWriteLock()
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
        val accessToken: String
        fun Interceptor.Chain.execute(authHeader: String, authValue: String): Response {
            return request().newBuilder()
                .header(authHeader, authValue)
                .removeHeader(API_KEY_INTERNAL_HEADER)
                .build().let { request ->
                    proceed(request)
                }
        }
        val response = if (isApiKeyAuth) {
            nameHeader = API_KEY_HEADER
            accessToken = apiKey
            chain.execute(nameHeader, apiKey)
        } else {
            tokenUpdateSyncer.read {
                val token = accountRepository.accessToken
                accessToken = token ?: ""
                nameHeader = AUTHORIZATION_HEADER
                chain.execute(nameHeader, AUTH_HEADER_PREFIX + token)
            }
        }
        if ((response.code() == HTTP_UNAUTHORIZED) && !isApiKeyAuth) {
            val accountRepository = component().accountManagerModule.accountRepository
            val refreshToken = accountRepository.refreshToken
            if (refreshToken != null) {
                tokenUpdateSyncer.write {
                    val currentToken = accountRepository.accessToken
                    if (accessToken != currentToken) return@write
                    run {
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
                                    .header(API_KEY_HEADER, apiKey)
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