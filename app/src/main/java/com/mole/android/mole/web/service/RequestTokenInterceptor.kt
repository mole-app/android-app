package com.mole.android.mole.web.service

import com.mole.android.mole.component
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class RequestTokenInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val accountManager = component().accountManager
        val accounts = accountManager.getAccountsByType("com.mole.android.mole")

        val token: String?
        if (accounts.isNotEmpty()) {
            token = accountManager.peekAuthToken(accounts[0], "accessAuthToken")
        } else {
            token = null
        }

        val nameHeader: String
        val valueHeader: String
        if (token == null) {
            nameHeader = "x-api-key"
            valueHeader = "testAndroid"
        } else {
            nameHeader = "Authorization"
            valueHeader = "Bearer $token"
        }

        val request: Request = chain.request().newBuilder()
            .header(nameHeader, valueHeader)
            .build()

        // Here where we'll try to refresh token.
        // with an retrofit call
        // After we succeed we'll proceed our request

        return chain.proceed(request)
    }
}