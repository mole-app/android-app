package com.mole.android.mole.di

import com.google.firebase.installations.FirebaseInstallations

class FirebaseModule : Module() {
    val instInstallation by lazy {
        FirebaseInstallations.getInstance()
    }
}