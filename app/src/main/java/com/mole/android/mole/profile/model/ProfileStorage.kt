package com.mole.android.mole.profile.model

import com.mole.android.mole.profile.data.ProfileUserInfo

interface ProfileStorage {
    suspend fun get(): ProfileUserInfo?
    suspend fun set(info: ProfileUserInfo)
}