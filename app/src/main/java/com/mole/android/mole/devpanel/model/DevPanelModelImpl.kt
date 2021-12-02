package com.mole.android.mole.devpanel.model

import com.mole.android.mole.di.AccountManagerModule

class DevPanelModelImpl(private val accountManagerModule: AccountManagerModule) : DevPanelModel {
    override fun getAccessToken(): String? {
        return accountManagerModule.accessToken
    }

    override fun setAccessToken(token: String?) {
        accountManagerModule.accessToken = token
    }

    override fun getRefreshToken(): String? {
        return accountManagerModule.refreshToken
    }

    override fun setRefreshToken(token: String?) {
        accountManagerModule.refreshToken = token
    }

}
