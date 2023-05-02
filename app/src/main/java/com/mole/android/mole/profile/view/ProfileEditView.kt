package com.mole.android.mole.profile.view

import com.mole.android.mole.MoleBaseView
import com.mole.android.mole.profile.data.ProfileUserInfo

interface ProfileEditView : MoleBaseView {
    fun showInitData(editUserInfo: ProfileUserInfo)
    fun showError()
    fun closeScreen()
}
