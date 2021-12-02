package com.mole.android.mole.devpanel.model

interface DevPanelModel {
    companion object {
        const val CORRUPTED_PART = "jgfsf78gfie4bfgqt8436ghf9q34fqo8fon"
    }

    fun getAccessToken(): String?

    fun setAccessToken(token: String?)

    fun getRefreshToken(): String?

    fun setRefreshToken(token: String?)
}
