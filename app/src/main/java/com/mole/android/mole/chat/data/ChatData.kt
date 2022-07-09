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
        val id: Int,
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
    ChatData.ChatMessage(100, false, +1000, "ресторан", false, Date()),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(99, true, -1000, "ресторан1", true, Date()),
    ChatData.ChatMessage(98,true, -1000, "ресторан2", false, Date()),
    ChatData.ChatMessage(97, false, +1000),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(96, false, -1000),
    ChatData.ChatMessage(95, false, -1000),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(94, false, +1000),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(93, false, -1000),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(92, true, +1800),
    ChatData.ChatMessage(91, true, +150),
    ChatData.ChatDate(Date()),
    ChatData.ChatMessage(90, false, -800),
    ChatData.ChatMessage(89, false, +1000),
    ChatData.ChatMessage(88, true, -150),
    ChatData.ChatDate(Date()),

)
