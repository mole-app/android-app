package com.mole.android.mole.chat.data

import com.mole.android.mole.dateToString
import com.mole.android.mole.timeToString
import java.util.*

sealed class ChatData {
    data class ChatDate(
        private val remoteDate: Date
    ) : ChatData() {
        val date: String
            get() = dateToString(remoteDate)
    }

    data class ChatMessage(
        val isMessageOfUser: Boolean,
        val debtValue: Int,
        val tag: String = "",
        val isRead: Boolean = true,
        private val remoteTime: Date = Date()
    ) : ChatData() {
        val time: String
            get() = timeToString(remoteTime)
    }
}

val testChatData = listOf<ChatData>(
    ChatData.ChatMessage(false, +1000, "ресторан", false, Date()),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(true, -1000, "ресторан1", true, Date()),
    ChatData.ChatMessage(true, -1000, "ресторан2", false, Date()),
    ChatData.ChatMessage(false, +1000),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(false, -1000),
    ChatData.ChatMessage(false, -1000),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(false, +1000),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(false, -1000),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(true, +1800),
    ChatData.ChatMessage(true, +150),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(false, -800),
    ChatData.ChatMessage(false, +1000),
    ChatData.ChatMessage(true, -150),
    ChatData.ChatDate(Date()),

)
