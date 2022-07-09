package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatDataConverter
import com.mole.android.mole.chat.data.ChatDataDomain
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
        isLoadUserInfo: Boolean,
        idDebtMax: Int?
    ): ApiResult<ChatModel.SuccessChatResult> {
        val task = mainScope.async(Dispatchers.IO) {
            try {
                if (debtLeft != 0) {
                    val serverData: ChatDataDomain = if (idDebtMax != null) {
                        service.getChatDataBeforeIdDebtMax(userId, idDebtMax, LIMIT)
                    } else {
                        service.getChatData(userId, LIMIT)
                    }
                    debtLeft = serverData.debtLeft
                    if (isLoadUserInfo) {
                        ApiResult.create<ChatModel.SuccessChatResult>(
                            ChatModel.SuccessChatResult.DataWithUserInfo(
                                chatData = ChatDataConverter.convertDebtDomainToChatData(
                                    debtsDomain = serverData.debts,
                                    userId = serverData.debtor.debtorInfo.idUser
                                ),
                                userInfo = ChatDataConverter.convertDebtorDomainToUserInfo(
                                    debtor = serverData.debtor
                                )
                            )
                        )
                    } else {
                        ApiResult.create<ChatModel.SuccessChatResult>(
                            ChatModel.SuccessChatResult.DataBatch(
                                ChatDataConverter.convertDebtDomainToChatData(
                                    debtsDomain = serverData.debts,
                                    userId = serverData.debtor.debtorInfo.idUser
                                )
                            )
                        )
                    }
                } else {
                    ApiResult.create<ChatModel.SuccessChatResult>(
                        ChatModel.SuccessChatResult.DataIsOver
                    )
                }
            } catch (exception: HttpException) {
                ApiResult.create(ApiResult.MoleError(exception.code(), exception.message()))
            }
        }
        return task.await()
    }

    companion object {
        private const val LIMIT = 30
        private var debtLeft: Int = -1
    }
}