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
            model.getProfileInfo().withResult { result ->
                val profileUserInfo = result.profileUserInfo
                view.setProfileLogin(profileUserInfo.login)
                view.setProfileName(profileUserInfo.name)
                view.setIcon(profileUserInfo.photoNormal)
                view.setTags(profileUserInfo.tags)
                view.setTotalDebtsSummary(profileUserInfo.totalSum)
            } .withError {
                view.showSnackBar(it.description)
            }
        }
    }
}