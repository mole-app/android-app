package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.web.service.ApiResult

interface ChatModel {
    suspend fun loadNextData(leftCountData: Int): ApiResult<SuccessChatResult>
    sealed class SuccessChatResult {
        object SuccessDataAlreadyLoaded : SuccessChatResult()
        class SuccessLoadData(val chatData: List<ChatData>) : SuccessChatResult()
    }
}