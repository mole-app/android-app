package com.mole.android.mole.di

import com.google.firebase.installations.FirebaseInstallations

class FirebaseModule {
    var fingerprint: String? = null

    val instInstallation by lazy {
        FirebaseInstallations.getInstance()
    }

    init {
        instInstallation.id.addOnSuccessListener {
            fingerprint = it
        }
    }
}