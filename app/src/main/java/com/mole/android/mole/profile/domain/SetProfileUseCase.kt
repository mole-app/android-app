package com.mole.android.mole.profile.domain

import com.mole.android.mole.profile.data.ProfileUserInfo
import com.mole.android.mole.profile.model.ProfileModel
import com.mole.android.mole.profile.model.ProfileStorage

interface SetProfileUseCase {
    suspend fun invoke(info: ProfileUserInfo, isNewLogin: Boolean)
}

class SetProfileUseCaseImpl(
    private val storage: ProfileStorage,
    private val model: ProfileModel
) : SetProfileUseCase {
    override suspend fun invoke(info: ProfileUserInfo, isNewLogin: Boolean) {
        if (isNewLogin) {
            model.setProfileInfo(name = info.name, login = info.login)
        } else {
            model.setProfileInfo(name = info.name)
        }
        storage.set(info)
    }
}
