package com.mole.android.mole.chat.data

import com.mole.android.mole.stringToDate
import java.util.*

object ChatDataConverter {

    fun debtsAsDomain(
        debtsDomain: List<ChatDataDebtRemote>?,
        userId: Int
    ): List<ChatDebtsData> {
        val debts: MutableList<ChatDebtsData> = mutableListOf()
        return if (debtsDomain.isNullOrEmpty()) {
            debts
        } else {
            var lastDate: Date = stringToDate(debtsDomain[0].createTime)
            for (debtDomain in debtsDomain) {
                val newDate = stringToDate(debtDomain.createTime)
                if (isNewDate(newDate, lastDate)) {
                    debts.add(ChatDebtsData.ChatDate(lastDate))
                }
                lastDate = newDate

                val isMessageOfUser = userId == debtDomain.idUser
                debts.add(
                    ChatDebtsData.ChatMessage(
                        id = debtDomain.id,
                        isMessageOfUser = isMessageOfUser,
                        debtValue = calculateDebtValue(
                            isMessageOfUser,
                            debtDomain.debtType,
                            debtDomain.sum
                        ),
                        tag = debtDomain.tag,
                        isRead = true,
                        remoteTime = newDate,
                        isDeleted = debtDomain.isDelete
                    )
                )
                if (debtDomain.id == debtsDomain.last().id) {
                    debts.add(ChatDebtsData.ChatDate(lastDate))
                }
            }
            debts
        }

    }

    private fun isNewDate(newDate: Date, oldDate: Date): Boolean {
        val newDateWithoutTime = removeTime(newDate)
        val oldDateWithoutTime = removeTime(oldDate)
        return (oldDateWithoutTime.time - newDateWithoutTime.time >= MILLIS_IN_DAY)

    }

    private fun removeTime(date: Date): Date {
        val currentTime = date.time
        val dateInMillisWithoutTime = (currentTime / MILLIS_IN_DAY) * MILLIS_IN_DAY
        return Date(dateInMillisWithoutTime)

    }

    private fun calculateDebtValue(messageOfUser: Boolean, debtType: String, debtSum: Int): Int {
        return when (debtType) {
            DebtType.GIVE.stringValue -> {
                if (messageOfUser) -1 * debtSum
                else debtSum
            }
            DebtType.GET.stringValue -> {
                if (messageOfUser) debtSum
                else -1 * debtSum
            }
            else -> {
                if (messageOfUser) debtSum
                else -1 * debtSum
            }
        }
    }

    private const val MILLIS_IN_DAY = (60 * 60 * 24 * 1000).toLong()
}