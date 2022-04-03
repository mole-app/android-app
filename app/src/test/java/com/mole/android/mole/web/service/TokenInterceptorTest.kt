package com.mole.android.mole.web.service

import com.mole.android.mole.di.FingerprintRepository
import kotlinx.coroutines.*
import okhttp3.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.TimeUnit

class TokenInterceptorTest {
    private lateinit var thread1: Thread
    private lateinit var thread2: Thread

    @ObsoleteCoroutinesApi
    @Test
    fun tokenUpdateConcurrency() {
        val requestTokenInterceptor = RequestTokenInterceptor(FakeAccountRepository, FakeFingerprintRepository)
        requestTokenInterceptor.refreshService = FakeTokenRefreshService()
        thread1 = object : Thread() {
            override fun run() {
                super.run()
                requestTokenInterceptor.intercept(FakeChainTokenTest("http://google.com/1"))
            }
        }

        thread2 = object : Thread() {
            override fun run() {
                super.run()
                requestTokenInterceptor.intercept(FakeChainTokenTest("http://google.com/2"))
            }
        }


        thread1.start()

        thread1.join()
        thread2.join()

        assertEquals("Дважды обновлён токен2", tokenUpdated, 1)
    }

    companion object {
        const val TOKEN_PREFIX = "Bearer "
        const val NEW_ACCESS_TOKEN = "newAccess"
        const val OLD_ACCESS_TOKEN = "access"
        const val NEW_REFRESH_TOKEN = "newRefresh"
        const val OLD_REFRESH_TOKEN = "refresh"
    }

    var tokenUpdated = 0

    inner class FakeTokenRefreshService : TokenRefreshService {
        override suspend fun getNewAuthToken(
            refreshToken: String,
            fingerprint: String
        ): AuthTokenData {
//            assertEquals("Дважды обновлён токен", tokenUpdated, false)

            tokenUpdated++
            // Start 2 thread
            thread2.start()

            // Wait 2s for 2 thread begin get try access token
            Thread.sleep(2000)
            return AuthTokenData(NEW_ACCESS_TOKEN, NEW_REFRESH_TOKEN, "")
        }

    }

    inner class FakeChainTokenTest(val url: String) : Interceptor.Chain {
        override fun request(): Request {
            return Request.Builder().url(url).build()
        }

        override fun proceed(request: Request): Response {
            val header = request.header("Authorization")
            val outputCode: Int = when (header){
                TOKEN_PREFIX + NEW_ACCESS_TOKEN -> 200
                TOKEN_PREFIX + OLD_ACCESS_TOKEN -> 401
                else -> -1
            }
            return Response.Builder().request(request).protocol(Protocol.HTTP_1_1).message("Pichev na").code(outputCode).build()
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
        override var accessToken: String? = OLD_ACCESS_TOKEN
        override var refreshToken: String? = OLD_REFRESH_TOKEN

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
        override val fingerprint: String = "fingerprint"
    }

}