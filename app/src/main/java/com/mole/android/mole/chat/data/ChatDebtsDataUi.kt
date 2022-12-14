package com.mole.android.mole.chat.data

import com.mole.android.mole.dateToString
import com.mole.android.mole.timeToString
import java.util.*

sealed class ChatDebtsDataUi {
    data class ChatDate(
        private val remoteDate: Date
    ) : ChatDebtsDataUi() {
        val date: String
            get() = dateToString(remoteDate)
    }

    data class ChatMessage(
        val id: Int,
        val isMessageOfCreator: Boolean,
        val debtValue: Int,
        val tag: String? = "",
        val isRead: Boolean = true,
        val isDeleted: Boolean = false,
        private val remoteTime: Date = Date()
    ) : ChatDebtsDataUi() {
        val time: String
            get() = timeToString(remoteTime)
    }

    data class RepayDebt(
        val id: Int,
        val userName: String,
        val amount: Int,
        val fromCurrentUser: Boolean
    ) : ChatDebtsDataUi()

    object RetryData: ChatDebtsDataUi()
}