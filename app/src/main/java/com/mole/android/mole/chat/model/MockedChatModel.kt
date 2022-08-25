package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatDataDebtDomain
import com.mole.android.mole.chat.data.ChatDataDebtorDomain
import com.mole.android.mole.chat.data.ChatDataDomain
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.*
import retrofit2.HttpException
import java.util.*

class MockedChatModel(
    private val scope: CoroutineScope
) : ChatModel {

    override suspend fun loadChatData(
        userId: Int,
        idDebtMax: Int?
    ): ApiResult<SuccessChatResult> {
        return withContext(scope.coroutineContext) {
            call {
                delay(1000)
                testChatData
            }
        }
    }

    override suspend fun deleteItem(id: Int): ApiResult<SuccessDeleteResult> {
        return withContext(scope.coroutineContext + Dispatchers.IO) {
            try {
//                val deletedItem = testChatData[itemIndex]
//                (deletedItem as? ChatDebtsData.ChatMessage)?.isDisabled = true
                ApiResult.create(
                    SuccessDeleteResult
                )
            } catch (exception: HttpException) {
                ApiResult.create(ApiResult.MoleError(exception.code(), exception.message()))
            }
        }
    }
}

val testDebts = listOf<ChatDataDebtDomain>(
    ChatDataDebtDomain(100, false, +1000, "ресторан", false, false, Date()),
    ChatDataDebtDomain(99, true, -1000, "ресторан1", true, false, Date()),
    ChatDataDebtDomain(98, true, -1000, "ресторан2", false, false, Date()),
    ChatDataDebtDomain(97, false, +1000, "", false, false, Date()),
    ChatDataDebtDomain(96, false, -1000, "", false, false, Date()),
    ChatDataDebtDomain(95, false, -1000, "", false, false, Date()),
    ChatDataDebtDomain(94, false, +1000, "", false, false, Date()),
    ChatDataDebtDomain(93, false, -1000, "", false, false, Date()),
    ChatDataDebtDomain(92, true, +1800, "", false, false, Date()),
    ChatDataDebtDomain(91, true, +150, "", false, false, Date()),
    ChatDataDebtDomain(90, false, -800, "", false, false, Date()),
    ChatDataDebtDomain(89, false, +1000, "", false, false, Date()),
    ChatDataDebtDomain(88, true, -150, "", false, false, Date()),
)

val testDebtor = ChatDataDebtorDomain(
    id = 0,
    name = "Александр",
    avatarUrl = "",
    balance = 1500
)

val testChatData = ChatDataDomain(
    debts = testDebts,
    debtLeft = 0,
    debtor = testDebtor
)