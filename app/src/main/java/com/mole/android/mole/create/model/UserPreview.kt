package com.mole.android.mole.create.model

import com.mole.android.mole.profile.data.ProfilePhoto

data class UserPreview(
    val id: Int,
    val name: String,
    val login: String,
    val avatar: ProfilePhoto
)