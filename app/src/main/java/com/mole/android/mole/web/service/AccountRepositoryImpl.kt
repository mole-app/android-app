package com.mole.android.mole.web.service

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.mole.android.mole.BuildConfig

class AccountRepositoryImpl(
    context: Context,
    private val activity: AppCompatActivity?,
    private val googleSignInClient: GoogleSignInClient?
) : AccountRepository {

    companion object {
        const val ACCESS_TOKEN = "accessAuthToken"
        const val REFRESH_TOKEN = "refreshAuthToken"
        const val ACCOUNT_NAME = "Mole"
    }

    private val accountManager by lazy { AccountManager.get(context) }

    private val accounts: Array<Account>
        get() = accountManager.getAccountsByType(BuildConfig.APPLICATION_ID)


    private val account: Account?
        get() = accounts.firstOrNull()

    override var accessToken: String?
        get() {
            return if (accounts.isNotEmpty()) {
                val account = account ?: return null
                accountManager.peekAuthToken(account, ACCESS_TOKEN)
            } else {
                null
            }
        }
        set(value) {
            val account = account ?: return
            accountManager.setAuthToken(account, ACCESS_TOKEN, value)
        }

    override var refreshToken: String?
        get() {
            return if (accounts.isNotEmpty()) {
                val account = account ?: return null
                accountManager.peekAuthToken(account, REFRESH_TOKEN)
            } else {
                null
            }
        }
        set(value) {
            val account = account ?: return
            accountManager.setAuthToken(account, REFRESH_TOKEN, value)
        }

    override fun setEmptyListener(onEmpty: () -> Unit) {
        accountManager.addOnAccountsUpdatedListener(
            {
                if (accounts.isEmpty()) {
                    onEmpty()
                }
            },
            null,
            false
        )
    }

    override fun isHasAccount(): Boolean {
        return accounts.isNotEmpty()
    }

    override fun createAccount(accessToken: String, refreshToken: String): Boolean {
        val account = Account(ACCOUNT_NAME, BuildConfig.APPLICATION_ID)
        val success = accountManager.addAccountExplicitly(account, null, null)
        accountManager.setAuthToken(account, ACCESS_TOKEN, accessToken)
        accountManager.setAuthToken(account, REFRESH_TOKEN, refreshToken)
        return success
    }

    override fun removeAccount(onRemoved: () -> Unit) {
        val account = account ?: return
        accountManager.removeAccount(
            account,
            activity,
            { onRemoved() },
            null
        )
    }

    override fun removeAllAccount(onRemoved: () -> Unit) {
        googleSignInClient?.signOut()
        removeAccount { onRemoved() }
    }
}
