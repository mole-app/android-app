package com.mole.android.mole.di

import com.mole.android.mole.web.service.RetrofitBuilder

class RetrofitModule {
    companion object {
        const val VK_URL = "https://oauth.vk.com/authorize?" +
                "client_id=7843036" +
                "&display=mobile" +
                "&redirect_uri=https://mole-app.ru/android/auth" +
                "&scope=friends" +
                "&response_type=code" +
                "&v=5.131"
    }

    val retrofit by lazy { RetrofitBuilder.build() }
}