package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.asDomain
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ChatModelImplementation(
    private val service: ChatService,
    private val mainScope: CoroutineScope
) : ChatModel {

    override suspend fun loadChatData(
        userId: Int,
        idDebtMax: Int?
    ): ApiResult<SuccessChatResult> {
        val task = mainScope.async(Dispatchers.IO) {
            call {
                if (idDebtMax != null) {
                    service.getChatDataBeforeIdDebtMax(userId, LIMIT, idDebtMax).asDomain()
                } else {
                    service.getChatData(userId, LIMIT).asDomain()
                }
            }
        }
        return task.await()
    }

    companion object {
        private const val LIMIT = 30
    }
}