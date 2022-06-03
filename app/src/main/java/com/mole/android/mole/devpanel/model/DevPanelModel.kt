package com.mole.android.mole.devpanel.model

interface DevPanelModel {
    fun corruptedAccessToken()

    fun corruptedRefreshToken()

    fun removeAccount()

    fun removeRemoteAccount()

    fun isHasAccount(): Boolean
}
