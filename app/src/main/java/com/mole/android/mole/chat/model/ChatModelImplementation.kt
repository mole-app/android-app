package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.chat.data.testChatData

class ChatModelImplementation: ChatModel {
    override fun getChatData(): List<ChatData> {
        return testChatData
    }
}