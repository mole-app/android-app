package com.mole.android.mole.web.service

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.mole.android.mole.BuildConfig

class AccountRepository(context: Context, private val activity: AppCompatActivity?) {

    companion object {
        const val ACCESS_TOKEN = "accessAuthToken"
        const val REFRESH_TOKEN = "refreshAuthToken"
        const val ACCOUNT_NAME = "Mole"
    }

    private val accountManager by lazy { AccountManager.get(context) }

    private val accounts: Array<Account>
        get() = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID)


    private val account: Account
        get() = accounts[0]

    var accessToken: String?
        get() {
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
            return if (accounts.isNotEmpty()) {
                accountManager.peekAuthToken(accounts[0], REFRESH_TOKEN)
            } else {
                null
            }
        }
        set(value) {
            accountManager.setAuthToken(account, REFRESH_TOKEN, value)
        }

    fun setEmptyListener(onEmpty: () -> Unit) {
        accountManager.addOnAccountsUpdatedListener({ accounts ->
            if (accounts.isEmpty()) {
                onEmpty()
            }
        }, null, false)
    }

    fun isHasAccount(): Boolean {
        return accounts.isNotEmpty()
    }

    fun createAccount(accessToken: String, refreshToken: String): Boolean {
        val account = Account(ACCOUNT_NAME, BuildConfig.APPLICATION_ID)
        val success = accountManager.addAccountExplicitly(account, null, null)
        accountManager.setAuthToken(account, ACCESS_TOKEN, accessToken)
        accountManager.setAuthToken(account, REFRESH_TOKEN, refreshToken)
        return success
    }

    fun removeAccount(onRemoved: () -> Unit) {
        accountManager.removeAccount(
            account,  activity,
            { onRemoved() },
            null
        )
    }

}