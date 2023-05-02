package com.mole.android.mole.profile.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.profile.data.ProfileUserInfo
import com.mole.android.mole.profile.domain.GetProfileUseCase
import com.mole.android.mole.profile.domain.SetProfileUseCase
import com.mole.android.mole.profile.view.ProfileEditView
import kotlinx.coroutines.launch

class ProfileEditPresenter(
    private val getProfileUseCase: GetProfileUseCase,
    private val setProfileUseCase: SetProfileUseCase
) : MoleBasePresenter<ProfileEditView>() {

    private var editUserInfo: ProfileUserInfo? = null

    override fun attachView(view: ProfileEditView) {
        super.attachView(view)
        loadData()
    }

    fun onConfirmButtonClick(name: String, login: String) {
        withScope {
            launch {
                editUserInfo?.let {
                    setProfileUseCase.invoke(it.copy(name = name, login = login))
                }
                view?.closeScreen()
            }
        }
    }

    private fun loadData() {
        withScope {
            launch {
                getProfileUseCase.invoke()
                    .withResult { profile ->
                        editUserInfo = profile.profileUserInfo
                        view?.showInitData(profile.profileUserInfo)
                    }.withError {
                        view?.showError()
                    }
            }
        }
    }
}
