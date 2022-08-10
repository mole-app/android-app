package com.mole.android.mole.chat.data

import java.util.*

data class ChatDataDebtDomain(
    val id: Int,
    val isMessageOfUser: Boolean,
    val debtValue: Int,
    val tag: String? = "",
    val isRead: Boolean = true,
    val date: Date
)