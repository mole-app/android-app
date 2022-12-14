package com.mole.android.mole.profile.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.profile.domain.ObserveProfileUseCase
import com.mole.android.mole.profile.model.ProfileModel
import com.mole.android.mole.profile.view.ProfileView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfilePresenter(
    private val observeProfileUseCase: ObserveProfileUseCase
) : MoleBasePresenter<ProfileView>() {

    override fun attachView(view: ProfileView) {
        super.attachView(view)
        loadData(view)
    }

    fun onRetryClick() {
        withView { view ->
            loadData(view)
        }
    }

    private fun loadData(view: ProfileView) {
        withScope {
            launch {
                observeProfileUseCase.invoke().collect {
                    it.withResult { result ->
                        val profileUserInfo = result.profileUserInfo
                        view.showContent()
                        view.setProfileLogin(profileUserInfo.login)
                        view.setProfileName(profileUserInfo.name)
                        view.setIcon(profileUserInfo.photoNormal)
                        view.setTags(profileUserInfo.tags)
                        view.setTotalDebtsSummary(profileUserInfo.totalSum)
                    }.withError {
                        view.showError()
                    }
                }
            }
        }
    }
}