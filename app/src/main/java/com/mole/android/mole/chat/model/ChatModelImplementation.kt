package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.asDomain
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class ChatModelImplementation(
    private val service: ChatService,
    private val scope: CoroutineScope
) : ChatModel {

    override suspend fun loadChatData(
        userId: Int,
        idDebtMax: Int?
    ): ApiResult<SuccessChatResult> {
        val task = scope.async {
            call {
                if (idDebtMax != null) {
                    service.getChatDataBeforeIdDebtMax(4, LIMIT, idDebtMax).asDomain()
                } else {
                    service.getChatData(4, LIMIT).asDomain()
                }
            }
        }
        return task.await()
    }

    companion object {
        private const val LIMIT = 30
    }
}