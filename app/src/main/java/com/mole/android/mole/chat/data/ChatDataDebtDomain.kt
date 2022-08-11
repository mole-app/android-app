package com.mole.android.mole.chat.data

import java.util.*

data class ChatDataDebtDomain(
    val id: Int,
    val isMessageOfCreator: Boolean,
    val debtValue: Int,
    val tag: String? = "",
    val isRead: Boolean = true,
    val isDeleted: Boolean = false,
    val date: Date
)