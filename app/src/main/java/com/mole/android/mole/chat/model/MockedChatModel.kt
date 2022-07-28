package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.chat.data.testChatData
import com.mole.android.mole.chat.data.testChatUserInfo
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class MockedChatModel(
    private val mainScope: CoroutineScope
) : ChatModel {

    override suspend fun loadChatData(
        userId: Int,
        idDebtMax: Int?
    ): ApiResult<SuccessChatResult> {
        val task = mainScope.async(Dispatchers.IO) {
            call {
                delay(1000)
                ChatData(
                    testChatData,
                    testChatUserInfo,
                    1
                )
            }
        }
        return task.await()
    }
}