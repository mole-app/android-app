package com.mole.android.mole.profile.model

import com.mole.android.mole.profile.data.ProfileUserInfo
import java.util.concurrent.atomic.AtomicReference

class InMemoryProfileStorage : ProfileStorage {

    private val profileRef: AtomicReference<ProfileUserInfo> = AtomicReference()

    override suspend fun get(): ProfileUserInfo? {
       return profileRef.get()
    }

    override suspend fun set(info: ProfileUserInfo) {
        profileRef.set(info)
    }
}