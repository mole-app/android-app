package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.testChatData
import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.HttpException
import java.lang.Thread.sleep

class ChatModelImplementation(
    private val mainScope: CoroutineScope
) : ChatModel {

    override suspend fun loadNextData(leftCountData: Int): ApiResult<ChatModel.SuccessChatResult> {
        val task = mainScope.async(Dispatchers.IO) {
            try {
                sleep(1000)
                ApiResult.create<ChatModel.SuccessChatResult>(
                    ChatModel.SuccessChatResult.SuccessLoadData(
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