package com.mole.android.mole.di

import android.accounts.AccountManager
import android.content.Context

class AccountManagerModule(context: Context) {

    companion object {
        const val ACCESS_TOKEN = "accessAuthToken"
        const val REFRESH_TOKEN = "refreshAuthToken"
    }

    val accountManager by lazy { AccountManager.get(context) }
}