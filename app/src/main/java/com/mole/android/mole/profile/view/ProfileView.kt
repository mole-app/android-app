package com.mole.android.mole.profile.view

import com.mole.android.mole.MoleBaseView

interface ProfileView : MoleBaseView {
    fun setProfileName(name: String)

    fun setProfileLogin(login: String)

    fun setTotalDebtsSummary(summary: Long)

    fun setTags(tags: List<String>)

    fun setIcon(uri: String?)

    fun showError()

    fun showContent()
}