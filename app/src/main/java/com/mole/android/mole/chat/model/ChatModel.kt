package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatData

interface ChatModel {
    fun getChatData(): List<ChatData>
}