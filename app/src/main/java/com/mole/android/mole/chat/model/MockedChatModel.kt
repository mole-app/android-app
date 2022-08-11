package com.mole.android.mole.chat.model

import com.mole.android.mole.chat.data.ChatDataDebtDomain
import com.mole.android.mole.chat.data.ChatDataDebtorDomain
import com.mole.android.mole.chat.data.ChatDataDomain
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import java.util.*

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
                testChatData
            }
        }
        return task.await()
    }
}

val testDebts = listOf<ChatDataDebtDomain>(
    ChatDataDebtDomain(100, false, +1000, "ресторан", false, Date()),
    ChatDataDebtDomain(99, true, -1000, "ресторан1", true, Date()),
    ChatDataDebtDomain(98, true, -1000, "ресторан2", false, Date()),
    ChatDataDebtDomain(97, false, +1000, "", false, Date()),
    ChatDataDebtDomain(96, false, -1000, "", false, Date()),
    ChatDataDebtDomain(95, false, -1000, "", false, Date()),
    ChatDataDebtDomain(94, false, +1000, "", false, Date()),
    ChatDataDebtDomain(93, false, -1000, "", false, Date()),
    ChatDataDebtDomain(92, true, +1800, "", false, Date()),
    ChatDataDebtDomain(91, true, +150, "", false, Date()),
    ChatDataDebtDomain(90, false, -800, "", false, Date()),
    ChatDataDebtDomain(89, false, +1000, "", false, Date()),
    ChatDataDebtDomain(88, true, -150, "", false, Date()),
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