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
        val tag: String = "",
        val isRead: Boolean = false,
        private val remoteDate: Date = Date()
    ) : ChatData() {
        val time: String
            get() = timeToString(remoteDate)
    }
}

val testChatData = mutableListOf<ChatData>(
    ChatData.ChatMessage(false, +1000),
    ChatData.ChatDate("03.04.2022"),
    ChatData.ChatMessage(false, -1000),
    ChatData.ChatDate("02.04.2022"),
    ChatData.ChatMessage(false, +1000),
    ChatData.ChatDate("01.04.2022"),
    ChatData.ChatMessage(false, -1000),
    ChatData.ChatDate("31.03.2022"),
    ChatData.ChatMessage(false, +1000),
    ChatData.ChatDate("30.03.2022"),
    ChatData.ChatMessage(false, -1000),
    ChatData.ChatDate("29.03.2022"),
    ChatData.ChatMessage(true, +1800),
    ChatData.ChatMessage(true, +150),
    ChatData.ChatDate("27.03.2022"),
    ChatData.ChatMessage(false, -800),
    ChatData.ChatMessage(false, +1000),
    ChatData.ChatMessage(true, -150),
    ChatData.ChatDate("26.03.2022"),

)
