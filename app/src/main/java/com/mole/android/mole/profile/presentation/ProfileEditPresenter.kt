package com.mole.android.mole.profile.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.profile.data.ProfileEditUserInfo
import com.mole.android.mole.profile.data.toEditUserInfo
import com.mole.android.mole.profile.domain.GetProfileUseCase
import com.mole.android.mole.profile.view.ProfileEditView
import kotlinx.coroutines.launch

class ProfileEditPresenter(
    private val getProfileUseCase: GetProfileUseCase,
) : MoleBasePresenter<ProfileEditView>() {

    private var editUserInfo: ProfileEditUserInfo? = null

    override fun attachView(view: ProfileEditView) {
        super.attachView(view)
        loadData()
    }

    fun onBackPressed(): ProfileEditUserInfo? {
        return editUserInfo
    }

    private fun loadData() {
        withScope {
            launch {
                getProfileUseCase.invoke()
                    .withResult { profile ->
                        editUserInfo = profile.profileUserInfo.toEditUserInfo()
                        view?.showInitData(profile.profileUserInfo.toEditUserInfo())
                    }.withError {
                        view?.showError()
                    }
            }
        }
    }
}
