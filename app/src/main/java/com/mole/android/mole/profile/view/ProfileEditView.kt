package com.mole.android.mole.profile.view

import com.mole.android.mole.MoleBaseView
import com.mole.android.mole.profile.data.ProfileEditUserInfo

interface ProfileEditView : MoleBaseView {
    fun showInitData(editUserInfo: ProfileEditUserInfo)
    fun showError()
    fun backPressed(editUserInfo: ProfileEditUserInfo)
}
