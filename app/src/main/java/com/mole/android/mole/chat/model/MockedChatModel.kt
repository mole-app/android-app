package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.chat.data.ChatDebtsData
import com.mole.android.mole.chat.data.testChatData
import com.mole.android.mole.chat.data.testChatUserInfo
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import retrofit2.HttpException

class MockedChatModel(
    private val scope: CoroutineScope
) : ChatModel {

    override suspend fun loadChatData(
        userId: Int,
        idDebtMax: Int?
    ): ApiResult<SuccessChatResult> {
        val task = scope.async {
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

    override suspend fun deleteItem(id: Int): ApiResult<SuccessDeleteResult> {
        val task = scope.async(Dispatchers.IO) {
            try {
                Thread.sleep(1000)
                var itemIndex = -1

                testChatData.filterIndexed { index, chatData ->
                    (chatData as? ChatDebtsData.ChatMessage).run {
                        this?.id == id
                    }.also {
                        if (it) itemIndex = index
                    }
                }

//                val deletedItem = testChatData[itemIndex]
//                (deletedItem as? ChatDebtsData.ChatMessage)?.isDisabled = true
                ApiResult.create(
                    SuccessDeleteResult
                )
            } catch (exception: HttpException) {
                ApiResult.create(ApiResult.MoleError(exception.code(), exception.message()))
            }
        }
        return task.await()
    }
}