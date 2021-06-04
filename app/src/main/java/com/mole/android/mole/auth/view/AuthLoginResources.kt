package com.mole.android.mole.auth.view

import android.content.Context
import com.mole.android.mole.R

interface AuthLoginResources {
    val loginPrefix: String
}

class AuthLoginResourcesImplementation(context: Context) : AuthLoginResources {
    override val loginPrefix: String = context.getString(R.string.login_prefix)
}
