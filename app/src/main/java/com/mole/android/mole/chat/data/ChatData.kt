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
        val isMessageOfTheDebtor: Boolean,
        val debtValue: Int,
        val id: Int,
        val tag: String = "",
        val isRead: Boolean = true,
        private val remoteDate: Date = Date()
    ) : ChatData() {
        val time: String
            get() = timeToString(remoteDate)
    }
}

val testChatData = listOf(
    ChatData.ChatMessage(false, +1000, 1, "ресторан", false, Date()),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(true, -1000, 2, "ресторан1", true, Date()),
    ChatData.ChatMessage(true, -1000, 3, "ресторан2", false, Date()),
    ChatData.ChatMessage(false, +1000, 4),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(false, -1000, 5),
    ChatData.ChatMessage(false, -1000, 6),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(false, +1000, 7),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(false, -1000, 8),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(true, +1800, 9),
    ChatData.ChatMessage(true, +150, 10),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(false, -800, 11),
    ChatData.ChatMessage(false, +1000, 12),
    ChatData.ChatMessage(true, -150, 13),
    ChatData.ChatDate(Date()),

)
