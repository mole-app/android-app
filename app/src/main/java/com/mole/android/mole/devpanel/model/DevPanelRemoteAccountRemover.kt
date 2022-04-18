package com.mole.android.mole.devpanel.model

import com.google.gson.Gson
import com.mole.android.mole.BuildConfig
import com.mole.android.mole.await
import com.mole.android.mole.component
import com.mole.android.mole.profile.data.ProfileUserInfoDomain
import com.mole.android.mole.web.service.AuthTokenData
import com.mole.android.mole.web.service.RequestTokenInterceptor
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.HttpURLConnection.HTTP_OK

object DevPanelRemoteAccountRemover {

    private const val API_KEY_HEADER = "x-api-key"
    private const val SCHEME = "https"
    private const val HOST = "mole-app-dev.ru"
    private const val PORT = 8443
    private const val ID_USER_QUERY = "idUser"
    private const val DELETE_USER_URL = "api/db/deleteUser"
    private const val USER_INFO_URL = "api/profile/getProfileInfo"
    private const val REFRESH_TOKEN_QUERY = "refreshToken"
    private const val FINGERPRINT_TOKEN_QUERY = "fingerprint"
    private const val UPDATE_TOKEN_URL = "api/auth/refreshToken"
    private val okHttpClient: OkHttpClient = OkHttpClient()
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val AUTH_HEADER_PREFIX = "Bearer "

    suspend fun remove() {
        val userInfoUrl: HttpUrl = HttpUrl.Builder()
            .scheme(SCHEME)
            .host(HOST)
            .port(PORT)
            .addPathSegments(USER_INFO_URL)
            .build()

        val userInfoRequest =
            Request.Builder().url(userInfoUrl)
                .header(
                    AUTHORIZATION_HEADER,
                    AUTH_HEADER_PREFIX + component().accountManagerModule.accountRepository.accessToken!!
                )
                .build()

        var userInfoResponse = okHttpClient.newCall(userInfoRequest).await()

        if (userInfoResponse.code() != HTTP_OK) {
            val authTokenDataUrl: HttpUrl = HttpUrl.Builder()
                .scheme(SCHEME)
                .host(HOST)
                .port(PORT)
                .addPathSegments(UPDATE_TOKEN_URL)
                .addQueryParameter(
                    REFRESH_TOKEN_QUERY,
                    component().accountManagerModule.accountRepository.refreshToken
                )
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
                okHttpClient.newCall(authTokenDataRequest).await()

            val authTokenData = Gson().fromJson(
                authTokenDataResponse.body()?.string(),
                AuthTokenData::class.java
            )

            userInfoResponse = okHttpClient.newCall(
                Request.Builder().url(userInfoUrl)
                    .header(
                        AUTHORIZATION_HEADER,
                        AUTH_HEADER_PREFIX + authTokenData.accessToken
                    )
                    .build()
            ).await()
        }

        val profileUserInfo = Gson().fromJson(
            userInfoResponse.body()?.string(),
            ProfileUserInfoDomain::class.java
        )

        val userDeleteUrl: HttpUrl = HttpUrl.Builder()
            .scheme(SCHEME)
            .host(HOST)
            .port(PORT)
            .addPathSegments(DELETE_USER_URL)
            .addQueryParameter(ID_USER_QUERY, profileUserInfo.profile.id.toString())
            .build()

        val userDeleteRequest = Request.Builder().url(userDeleteUrl).delete().build()
        val userDeleteResponse = okHttpClient.newCall(userDeleteRequest).await()

    }
}