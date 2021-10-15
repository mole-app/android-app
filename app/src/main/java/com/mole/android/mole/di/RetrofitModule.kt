package com.mole.android.mole.di

import com.mole.android.mole.web.service.RetrofitBuilder

class RetrofitModule: Module() {
    val retrofit by lazy { RetrofitBuilder.build() }
}