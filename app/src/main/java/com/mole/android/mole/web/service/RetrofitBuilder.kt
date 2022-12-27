package com.mole.android.mole.web.service

import android.app.Activity
import com.mole.android.mole.component
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.mole.android.mole.BuildConfig
import com.mole.android.mole.di.repository.PreferenceRepository
import com.mole.android.mole.di.repository.RepositoryKeys.baseHost
import com.mole.android.mole.di.repository.RepositoryKeys.baseHostDefault
import com.mole.android.mole.di.repository.RepositoryKeys.customPort
import com.mole.android.mole.di.repository.RepositoryKeys.customPortDefault
import com.mole.android.mole.di.repository.RepositoryKeys.customPortEnable
import com.mole.android.mole.di.repository.RepositoryKeys.customPortEnableDefault
import com.mole.android.mole.di.repository.RepositoryKeys.enableUnsecureDefault
import com.mole.android.mole.di.repository.RepositoryKeys.enableUnsecureKey
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitBuilder {
    const val API_PATH = "api"
    val SCHEME
        get() = if (isEnableUnsecure) SCHEME_UNSAFE else SCHEME_SAFE
    val HOST
        get() = repository.getString(baseHost, baseHostDefault)
    val PORT
        get() = if (isEnableCustomPort)
            repository.getInt(customPort, customPortDefault)
        else if (isEnableUnsecure)
            PORT_UNSAFE
        else
            PORT_SAFE


    private val repository by lazy { PreferenceRepository(component().activity as? Activity) }
    private val isEnableUnsecure by lazy {
        repository.getBoolean(enableUnsecureKey, enableUnsecureDefault)
    }
    private val isEnableCustomPort by lazy {
        repository.getBoolean(customPortEnable, customPortEnableDefault)
    }

    private val BASE_URL = "$SCHEME://$HOST:$PORT/$API_PATH/"

    private const val SCHEME_UNSAFE = "http"
    private const val PORT_UNSAFE = 8080
    private const val SCHEME_SAFE = "https"
    private const val PORT_SAFE = 8444

    private const val TIMEOUTS = 30L
    private const val MAX_CONTENT_LENGTH = 250000L

    val chuckerInterceptor by lazy {
        ChuckerInterceptor.Builder(component().context)
            .collector(ChuckerCollector(component().context))
            .maxContentLength(MAX_CONTENT_LENGTH)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    }

    fun build(): Retrofit {
        val tokenInterceptor = RequestTokenInterceptor(
            chuckerInterceptor,
            component().accountManagerModule.accountRepository,
            component().firebaseModule,
            component().buildConfigModule.X_API_KEY
        )

        val okHttpBuilder = OkHttpClient.Builder()
            .readTimeout(TIMEOUTS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUTS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUTS, TimeUnit.SECONDS)
            .addInterceptor(tokenInterceptor)
            .addInterceptor(chuckerInterceptor)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(interceptor)
        }

        val client = okHttpBuilder.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}