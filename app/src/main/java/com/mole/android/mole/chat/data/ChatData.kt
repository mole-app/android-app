package com.mole.android.mole.chat.data

sealed class ChatData {
    data class ChatDate(
        val date: String
    ) : ChatData()

    data class ChatMessage(
        val isMessageOfTheDebtor: Boolean,
        val debtValue: Int,
        val tag: String = "",
        val time: String =""
    ) : ChatData()
}

val testChatData = mutableListOf<ChatData>(
    ChatData.ChatDate("26.03.2022"),
    ChatData.ChatMessage(true, -150),
    ChatData.ChatMessage(false, +1000),
    ChatData.ChatMessage(false, -800),
    ChatData.ChatDate("27.03.2022"),
    ChatData.ChatMessage(false, -1000),
    ChatData.ChatMessage(true, +1800),
    ChatData.ChatMessage(true, +150),
    ChatData.ChatDate("29.03.2022")
)
