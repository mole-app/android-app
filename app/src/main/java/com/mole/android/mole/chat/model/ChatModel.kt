package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.web.service.ApiResult

interface ChatModel {
    suspend fun loadNextData(leftCountData: Int): ApiResult<SuccessChatResult>
    suspend fun deleteItem(id: Int): ApiResult<SuccessChatResult>

    sealed class SuccessChatResult {
        object ItemDeleted : SuccessChatResult()
        object DataIsOver : SuccessChatResult()
        class DataBatch(val chatData: List<ChatData>) : SuccessChatResult()
    }
}