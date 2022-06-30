package com.mole.android.mole.chat.data

data class ChatUserInfo(
    val id: Int,
    val name: String,
    val avatarUrl: String,
    val balance: Int
)

val testChatUserInfo = ChatUserInfo(
    id = 0,
    name = "Александр",
    avatarUrl = "",
    balance = 1500
)
