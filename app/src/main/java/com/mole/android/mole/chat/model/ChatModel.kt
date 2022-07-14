package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.create.model.CreatedDebt
import com.mole.android.mole.web.service.ApiResult

typealias SuccessChatResult = ChatData

interface ChatModel {
    suspend fun loadChatData(userId: Int, idDebtMax: Int?): ApiResult<SuccessChatResult>
}