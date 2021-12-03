package com.mole.android.mole.devpanel.model

import com.mole.android.mole.web.service.AccountRepository

class DevPanelModelImpl(private val accountRepository: AccountRepository) : DevPanelModel {
    override fun getAccessToken(): String? {
        return accountRepository.accessToken
    }

    override fun setAccessToken(token: String?) {
        accountRepository.accessToken = token
    }

    override fun getRefreshToken(): String? {
        return accountRepository.refreshToken
    }

    override fun setRefreshToken(token: String?) {
        accountRepository.refreshToken = token
    }

}
