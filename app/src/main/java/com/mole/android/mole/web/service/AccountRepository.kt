package com.mole.android.mole.web.service

interface AccountRepository {

    var accessToken: String?

    var refreshToken: String?

    fun setEmptyListener(onEmpty: () -> Unit)

    fun isHasAccount(): Boolean

    fun createAccount(accessToken: String, refreshToken: String): Boolean

    fun removeAccount(onRemoved: () -> Unit)

}