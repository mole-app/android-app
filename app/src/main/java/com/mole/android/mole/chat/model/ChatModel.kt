package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatDataDomain
import com.mole.android.mole.web.service.ApiResult

typealias SuccessDeleteResult = Unit
typealias SuccessChatResult = ChatDataDomain

interface ChatModel {
    suspend fun loadChatData(userId: Int, idDebtMax: Int?): ApiResult<SuccessChatResult>
    suspend fun deleteItem(id: Int): ApiResult<SuccessDeleteResult>
}