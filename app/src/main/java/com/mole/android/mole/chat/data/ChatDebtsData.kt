package com.mole.android.mole.chat.data

import com.mole.android.mole.dateToString
import com.mole.android.mole.timeToString
import java.util.*

sealed class ChatDebtsData {
    data class ChatDate(
        private val remoteDate: Date
    ) : ChatDebtsData() {
        val date: String
            get() = dateToString(remoteDate)
    }

    data class ChatMessage(
        val id: Int,
        val isMessageOfUser: Boolean,
        val debtValue: Int,
        val tag: String? = "",
        val isRead: Boolean = true,
        val isDeleted: Boolean = false,
        private val remoteTime: Date = Date()
    ) : ChatDebtsData() {
        val time: String
            get() = timeToString(remoteTime)
    }
}

val testChatData = listOf<ChatDebtsData>(
    ChatDebtsData.ChatMessage(100, false, +1000, "ресторан", false, false, Date()),
    ChatDebtsData.ChatDate(Date()),
    ChatDebtsData.ChatMessage(99, true, -1000, "ресторан1", true, true, Date()),
    ChatDebtsData.ChatMessage(98,true, -1000, "ресторан2", false, false, Date()),
    ChatDebtsData.ChatMessage(97, false, +1000),
    ChatDebtsData.ChatDate(Date()),
    ChatDebtsData.ChatMessage(96, false, -1000),
    ChatDebtsData.ChatMessage(95, false, -1000),
    ChatDebtsData.ChatDate(Date()),
    ChatDebtsData.ChatMessage(94, false, +1000),
    ChatDebtsData.ChatDate(Date()),
    ChatDebtsData.ChatMessage(93, false, -1000),
    ChatDebtsData.ChatDate(Date()),
    ChatDebtsData.ChatMessage(92, true, +1800),
    ChatDebtsData.ChatMessage(91, true, +150),
    ChatDebtsData.ChatDate(Date()),
    ChatDebtsData.ChatMessage(90, false, -800),
    ChatDebtsData.ChatMessage(89, false, +1000),
    ChatDebtsData.ChatMessage(88, true, -150),
    ChatDebtsData.ChatDate(Date()),

)
