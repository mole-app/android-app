package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.chat.data.ChatUserInfo
import com.mole.android.mole.web.service.ApiResult

interface ChatModel {
    suspend fun loadChatData(userId: Int, isLoadUserInfo: Boolean, idDebtMax: Int?): ApiResult<SuccessChatResult>
    sealed class SuccessChatResult {
        object DataIsOver : SuccessChatResult()
        class DataBatch(val chatData: List<ChatData>) : SuccessChatResult()
        class DataWithUserInfo(val chatData: List<ChatData>, val userInfo: ChatUserInfo) :
            SuccessChatResult()
    }
}