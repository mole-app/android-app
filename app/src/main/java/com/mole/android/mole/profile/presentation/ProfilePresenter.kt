package com.mole.android.mole.profile.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.profile.view.ProfileView

class ProfilePresenter: MoleBasePresenter<ProfileView>() {
    override fun attachView(view: ProfileView) {
        super.attachView(view)
        view.setProfileLogin("norm")
        view.setProfileName("Bob")
        view.setTags(listOf("cafe", "hookah"))
        view.setTotalDebtsSummary(10000)
    }
}