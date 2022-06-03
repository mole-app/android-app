package com.mole.android.mole.devpanel.model

import com.mole.android.mole.web.service.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DevPanelModelImpl(
    private val accountRepository: AccountRepository,
    private val scope: CoroutineScope
) : DevPanelModel {
    companion object {
        private const val CORRUPTED_PART = "jgfsf78gfie4bfgqt8436ghf9q34fqo8fon"
    }

    override fun corruptedAccessToken() {
        val accessToken = accountRepository.accessToken
        val corruptedAccessToken =
            accessToken?.removeRange(
                accessToken.length / 2,
                accessToken.length
            ) + CORRUPTED_PART
        accountRepository.accessToken = corruptedAccessToken
    }

    override fun corruptedRefreshToken() {
        val refreshToken = accountRepository.refreshToken
        val corruptedAccessToken =
            refreshToken?.removeRange(
                refreshToken.length / 2,
                refreshToken.length
            ) + CORRUPTED_PART
        accountRepository.refreshToken = corruptedAccessToken
    }

    override fun removeAccount() {
        accountRepository.removeAccount {}
    }

    override fun removeRemoteAccount() {
        scope.launch {
            DevPanelRemoteAccountRemover.remove()
            removeAccount()
        }
    }

    override fun isHasAccount(): Boolean {
        return accountRepository.isHasAccount()
    }
}
