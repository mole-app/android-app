package com.mole.android.mole.web.service

import com.mole.android.mole.component
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuilder {
    const val SCHEME = "http"
//    const val SCHEME = "https"
    const val HOST = "mole-app-dev.ru"
    const val PORT = 8080
//    const val PORT = 8443
    const val API_PATH = "api"

    private const val BASE_URL = "$SCHEME://$HOST:$PORT/$API_PATH/"

    fun build(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val chuckerInterceptor = ChuckerInterceptor.Builder(component().context)
            .collector(ChuckerCollector(component().context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()

        val tokenInterceptor = RequestTokenInterceptor(
            chuckerInterceptor,
            component().accountManagerModule.accountRepository,
            component().firebaseModule
        )

        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(chuckerInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}