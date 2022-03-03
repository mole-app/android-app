package com.mole.android.mole.profile.view

import android.net.Uri

interface ProfileView {
    fun setProfileName(name: String)

    fun setProfileLogin(login: String)

    fun setTotalDebtsSummary(summary: Int)

    fun setTags(tags: List<String>)

    fun setIcon(uri: Uri?)
}