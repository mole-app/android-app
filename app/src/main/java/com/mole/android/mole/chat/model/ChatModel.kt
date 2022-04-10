package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.web.service.ApiResult

interface ChatModel {
    suspend fun loadNextData(): ApiResult<SuccessChatResult>
    class SuccessChatResult(val chatData: List<ChatData>)
}