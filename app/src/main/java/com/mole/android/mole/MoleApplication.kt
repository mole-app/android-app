package com.mole.android.mole

import android.annotation.SuppressLint
import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.request.CachePolicy
import com.mole.android.mole.di.MoleComponent
import java.lang.IllegalStateException

class MoleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        val imageLoader = ImageLoader.Builder(applicationContext)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
        Coil.setImageLoader(imageLoader)
        component = MoleComponent(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        component = null
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var component: MoleComponent? = null
        fun requireComponent(): MoleComponent {
            return component ?: throw IllegalStateException("Dependencies component not exist")
        }
    }
}