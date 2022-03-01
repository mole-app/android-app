package com.mole.android.mole.profile.view

interface ProfileView {
    fun setProfileName(name: String)

    fun setProfileLogin(login: String)

    fun setTotalDebtsSummary(summary: Int)

    fun setTags(tags: List<String>)
}