package com.mole.android.mole.profile.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.profile.model.ProfileModel
import com.mole.android.mole.profile.view.ProfileView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ProfilePresenter(
    private val model: ProfileModel,
    private val scope: CoroutineScope,
) : MoleBasePresenter<ProfileView>() {
    override fun attachView(view: ProfileView) {
        super.attachView(view)
        scope.launch {
            val profileUserInfo = model.getProfileInfo()
            view.setProfileLogin(profileUserInfo.profile.login)
            view.setProfileName(profileUserInfo.profile.name)
            view.setIcon(profileUserInfo.photo.photoNormal)
        }
        view.setTags(listOf("cafe", "hookah", "fog"))
        view.setTotalDebtsSummary(-10000)
    }
}