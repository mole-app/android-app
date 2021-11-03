package com.mole.android.mole.web.service

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.NetworkErrorException
import android.content.Context
import android.os.Bundle

class Authenticator(context: Context) // Simple constructor
    : AbstractAccountAuthenticator(context) {

    // Editing properties is not supported
    override fun editProperties(r: AccountAuthenticatorResponse, s: String): Bundle {
        throw UnsupportedOperationException()
    }

    // Don't add additional accounts
    @Throws(NetworkErrorException::class)
    override fun addAccount(
        r: AccountAuthenticatorResponse,
        s: String,
        s2: String,
        strings: Array<String>,
        bundle: Bundle
    ): Bundle?  = null

    // Ignore attempts to confirm credentials
    @Throws(NetworkErrorException::class)
    override fun confirmCredentials(
        r: AccountAuthenticatorResponse,
        account: Account,
        bundle: Bundle
    ): Bundle?  = null

    // Getting an authentication token is not supported
    @Throws(NetworkErrorException::class)
    override fun getAuthToken(
        r: AccountAuthenticatorResponse,
        account: Account,
        s: String,
        bundle: Bundle
    ): Bundle {
        throw UnsupportedOperationException()
    }

    // Getting a label for the auth token is not supported
    override fun getAuthTokenLabel(s: String): String {
        throw UnsupportedOperationException()
    }

    // Updating user credentials is not supported
    @Throws(NetworkErrorException::class)
    override fun updateCredentials(
        r: AccountAuthenticatorResponse,
        account: Account,
        s: String,
        bundle: Bundle
    ): Bundle {
        throw UnsupportedOperationException()
    }

    // Checking features for the account is not supported
    @Throws(NetworkErrorException::class)
    override fun hasFeatures(
        r: AccountAuthenticatorResponse,
        account: Account,
        strings: Array<String>
    ): Bundle {
        throw UnsupportedOperationException()
    }
}