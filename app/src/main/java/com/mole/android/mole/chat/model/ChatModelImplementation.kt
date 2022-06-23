package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatDataConverter
import com.mole.android.mole.chat.data.ChatUserInfo
import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.HttpException

class ChatModelImplementation(
    private val service: ChatService,
    private val mainScope: CoroutineScope
) : ChatModel {

    override suspend fun loadChatData(
        userId: Int,
        isLoadUserInfo: Boolean
    ): ApiResult<ChatModel.SuccessChatResult> {
        val task = mainScope.async(Dispatchers.IO) {
            try {
                val serverData = service.getChatData(userId)
                when {
                    isLoadUserInfo -> {
                        ApiResult.create<ChatModel.SuccessChatResult>(
                            ChatModel.SuccessChatResult.DataWithUserInfo(
                                chatData = ChatDataConverter.convertDebtDomainToChatData(
                                    debtsDomain = serverData.debts,
                                    userId = serverData.debtor.debtorInfo.idUser
                                ),
                                userInfo = ChatUserInfo()
                            )
                        )
                    }
                    serverData.debtLeft <= 0 -> {
                        ApiResult.create<ChatModel.SuccessChatResult>(
                            ChatModel.SuccessChatResult.DataIsOver
                        )
                    }

                    else -> {
                        ApiResult.create<ChatModel.SuccessChatResult>(
                            ChatModel.SuccessChatResult.DataBatch(
                                ChatDataConverter.convertDebtDomainToChatData(
                                    debtsDomain = serverData.debts,
                                    userId = serverData.debtor.debtorInfo.idUser
                                )
                            )
                        )
                    }
                }
            } catch (exception: HttpException) {
                ApiResult.create(ApiResult.MoleError(exception.code(), exception.message()))
            }
        }
        return task.await()
    }
}