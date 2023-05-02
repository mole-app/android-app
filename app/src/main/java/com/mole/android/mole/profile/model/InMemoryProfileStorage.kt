package com.mole.android.mole.profile.model

import com.mole.android.mole.profile.data.ProfileUserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.atomic.AtomicReference

class InMemoryProfileStorage : ProfileStorage {

    private val profileRef: AtomicReference<ProfileUserInfo> = AtomicReference()
    private var profileState: MutableStateFlow<ProfileUserInfo?> = MutableStateFlow(null)

    override suspend fun get(): ProfileUserInfo? {
        return profileRef.get()
    }

    override suspend fun set(info: ProfileUserInfo) {
        profileRef.set(info)
        profileState.emit(info)
    }

    override suspend fun getFresh(): StateFlow<ProfileUserInfo?> {
        return profileState
    }
}
