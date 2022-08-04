package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatData
import com.mole.android.mole.chat.data.ChatDebtsData
import com.mole.android.mole.chat.data.asDomain
import com.mole.android.mole.chat.data.testChatData
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.HttpException
import java.lang.Thread.sleep

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
                    service.getChatDataBeforeIdDebtMax(userId, LIMIT, idDebtMax).asDomain()
                } else {
                    service.getChatData(userId, LIMIT).asDomain()
                }
            }
        }
        return task.await()
    }

    override suspend fun deleteItem(id: Int): ApiResult<SuccessDeleteResult> {
        val task = scope.async(Dispatchers.IO) {
            try {
                sleep(1000)
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

    companion object {
        private const val LIMIT = 30
    }
}