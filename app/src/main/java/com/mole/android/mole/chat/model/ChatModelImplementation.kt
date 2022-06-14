package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatData
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

    override suspend fun deleteItem(id: Int): ApiResult<ChatModel.SuccessChatResult> {
        val task = mainScope.async(Dispatchers.IO) {
            try {
                sleep(1000)
                var itemIndex = -1

                testChatData.filterIndexed { index, chatData ->
                    (chatData as? ChatData.ChatMessage).run {
                        this?.id == id
                    }.also {
                        if (it) itemIndex = index
                    }
                }

                val deletedItem = testChatData[itemIndex]
                (deletedItem as? ChatData.ChatMessage)?.isDisabled = true
                ApiResult.create<ChatModel.SuccessChatResult>(
                    ChatModel.SuccessChatResult.ItemDeleted
                )
            } catch (exception: HttpException) {
                ApiResult.create(ApiResult.MoleError(exception.code(), exception.message()))
            }
        }
        return task.await()
    }
}