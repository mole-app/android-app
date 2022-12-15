package com.mole.android.mole.chat.data

data class ChatDataDebtorDomain(
    val id: Int,
    val name: String,
    val avatarUrl: ChatAvatarUrl,
    val balance: Int
)

data class ChatAvatarUrl(
    val urlSmall: String,
    val urlNormal: String,
)
