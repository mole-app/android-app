package com.mole.android.mole.web.service

import com.mole.android.mole.di.FingerprintRepository
import okhttp3.*
import org.junit.Test
import java.util.concurrent.TimeUnit

class TokenInterceptorTest {

    @Test
    fun tokenUpdateConcurrency() {
        val requestTokenInterceptor = RequestTokenInterceptor(FakeAccountRepository, FakeFingerprintRepository)
        requestTokenInterceptor.refreshService = FakeTokenRefreshService
        requestTokenInterceptor.intercept(FakeChainTokenTest)
    }

    object FakeTokenRefreshService : TokenRefreshService {
        override suspend fun getNewAuthToken(
            refreshToken: String,
            fingerprint: String
        ): AuthTokenData {
            return AuthTokenData("", "", "")
        }

    }

    object FakeChainTokenTest : Interceptor.Chain {
        override fun request(): Request {
            return Request.Builder().url("http://google.com").build()
        }

        override fun proceed(request: Request): Response {
            return Response.Builder().request(request).protocol(Protocol.HTTP_1_1).message("Pichev na").code(401).build()
        }

        override fun connection(): Connection? {
            TODO("Not yet implemented")
        }

        override fun call(): Call {
            TODO("Not yet implemented")
        }

        override fun connectTimeoutMillis(): Int {
            TODO("Not yet implemented")
        }

        override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
            TODO("Not yet implemented")
        }

        override fun readTimeoutMillis(): Int {
            TODO("Not yet implemented")
        }

        override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
            TODO("Not yet implemented")
        }

        override fun writeTimeoutMillis(): Int {
            TODO("Not yet implemented")
        }

        override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
            TODO("Not yet implemented")
        }
    }

    object FakeAccountRepository : AccountRepository {
        override var accessToken: String?
            get() = "access"
            set(value) {}
        override var refreshToken: String?
            get() = "refresh"
            set(value) {}

        override fun setEmptyListener(onEmpty: () -> Unit) {

        }

        override fun isHasAccount(): Boolean {
            return true
        }

        override fun createAccount(
            name: String,
            accessToken: String,
            refreshToken: String
        ): Boolean {
            return true
        }

        override fun removeAccount(onRemoved: () -> Unit) {
            onRemoved()
        }

    }

    object FakeFingerprintRepository : FingerprintRepository {
        override val fingerprint: String
            get() = "fingerprint"
    }

}