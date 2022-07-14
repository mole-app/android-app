package com.mole.android.mole.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig

object MoleFirebaseRemoteConfig : RemoteConfig {
    private val remoteConfig: FirebaseRemoteConfig
        get() = Firebase.remoteConfig

    override fun getGoogleEnable(): Boolean {
        return remoteConfig.getBoolean("googleAuthEnable")
    }
}