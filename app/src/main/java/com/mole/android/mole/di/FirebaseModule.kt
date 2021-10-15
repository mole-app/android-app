package com.mole.android.mole.di

import com.google.firebase.installations.FirebaseInstallations

class FirebaseModule {
    val instInstallation by lazy {
        FirebaseInstallations.getInstance()
    }
}