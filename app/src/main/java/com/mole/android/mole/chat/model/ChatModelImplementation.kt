package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.chat.data.testChatData
import com.mole.android.mole.web.service.ApiResult

class ChatModelImplementation: ChatModel {
    override suspend fun getChatData(): ApiResult<List<ChatData>> {
        return ApiResult.create(testChatData)
    }
}