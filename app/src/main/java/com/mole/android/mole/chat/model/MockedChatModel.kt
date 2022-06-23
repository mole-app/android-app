package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.testChatData
import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import retrofit2.HttpException

class MockedChatModel(
    private val mainScope: CoroutineScope
) : ChatModel {

    override suspend fun loadChatData(
        userId: Int,
        isLoadUserInfo: Boolean
    ): ApiResult<ChatModel.SuccessChatResult> {
        val task = mainScope.async(Dispatchers.IO) {
            try {
                delay(1000)
                ApiResult.create<ChatModel.SuccessChatResult>(
                    ChatModel.SuccessChatResult.DataBatch(
                        testChatData
                    )
                )
            } catch (exception: HttpException) {
                ApiResult.create(ApiResult.MoleError(exception.code(), exception.message()))
            }
        }
        return task.await()
    }
}