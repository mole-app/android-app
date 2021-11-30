package com.mole.android.mole.di

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import com.mole.android.mole.BuildConfig
import com.mole.android.mole.component

class AccountManagerModule(context: Context) {

    companion object {
        const val ACCESS_TOKEN = "accessAuthToken"
        const val REFRESH_TOKEN = "refreshAuthToken"
    }

    private val accountManager by lazy { AccountManager.get(context) }

    val account: Account
        get() {
            val accountManager = accountManager
            val accounts = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID)
            val account = accounts[0]
            return account
        }

    var accessToken: String?
        get() {
            val accounts = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID)
            return if (accounts.isNotEmpty()) {
                accountManager.peekAuthToken(accounts[0], ACCESS_TOKEN)
            } else {
                null
            }
        }
        set(value) {
            accountManager.setAuthToken(account, ACCESS_TOKEN, value)
        }

    var refreshToken: String?
        get() {
            val accounts = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID)
            return if (accounts.isNotEmpty()) {
                accountManager.peekAuthToken(accounts[0], REFRESH_TOKEN)
            } else {
                null
            }
        }
        set(value) {
            accountManager.setAuthToken(account, REFRESH_TOKEN, value)
        }

    val profileId: String?
        get() {
            val accounts = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID)
            return if (accounts.isNotEmpty()) {
                accountManager.getUserData(accounts[0], "profileId")
            } else {
                null
            }
        }

    fun isHasAccount(): Boolean {
        val accountManager = accountManager
        val accounts = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID)
        return accounts.isNotEmpty()
    }

    fun createAccount(name: String, accessToken: String, refreshToken: String): Boolean {
        val account = Account(name, BuildConfig.APPLICATION_ID)
        val success = accountManager.addAccountExplicitly(account, null, null)
        accountManager.setAuthToken(account, ACCESS_TOKEN, accessToken)
        accountManager.setAuthToken(account, ACCESS_TOKEN, refreshToken)
        return success
    }

    fun setProfileId(profileId: String) {
        accountManager.setUserData(account, "profileId", profileId)
    }

    fun removeAccount(onRemoved: () -> Unit) {
        val accountManager = component().accountManagerModule.accountManager
        val accounts = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID)
        val account = accounts[0]
        accountManager.removeAccount(
            account, requireActivity(),
            { onRemoved() },
            null
        )
    }

}