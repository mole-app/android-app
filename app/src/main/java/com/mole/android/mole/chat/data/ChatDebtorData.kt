package com.mole.android.mole.chat.data

data class ChatDebtorData(
    val id: Int,
    val name: String,
    val avatarUrl: String,
    val balance: Int
)

val testChatUserInfo = ChatDebtorData(
    id = 0,
    name = "Александр",
    avatarUrl = "",
    balance = 1500
)
