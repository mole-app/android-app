package com.mole.android.mole.profile.model

import com.mole.android.mole.profile.data.ProfileUserInfo
import kotlinx.coroutines.flow.StateFlow

interface ProfileStorage {
    suspend fun get(): ProfileUserInfo?
    suspend fun set(info: ProfileUserInfo)
    suspend fun getFresh(): StateFlow<ProfileUserInfo?>
}
