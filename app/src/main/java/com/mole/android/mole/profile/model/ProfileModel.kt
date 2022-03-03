package com.mole.android.mole.profile.model

import com.mole.android.mole.profile.data.ProfileUserInfo

interface ProfileModel {
    suspend fun getProfileInfo(): ProfileUserInfo
}