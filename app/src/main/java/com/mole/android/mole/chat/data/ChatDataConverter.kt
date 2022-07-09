package com.mole.android.mole.chat.data

import com.mole.android.mole.stringToDate
import java.util.*

object ChatDataConverter {

    fun convertDebtorDomainToUserInfo(debtor: ChatDataDebtorDomain): ChatUserInfo {
        return ChatUserInfo(
            id = debtor.debtorInfo.idUser,
            name = debtor.debtorInfo.name,
            avatarUrl = debtor.mainPhotoUrl.photoSmall,
            balance = debtor.debtorStatistic.debtSum
        )
    }

    fun convertDebtDomainToChatData(
        debtsDomain: List<ChatDataDebtDomain>,
        userId: Int
    ): List<ChatData> {
        val debts: MutableList<ChatData> = mutableListOf()
        var lastDate: Date = stringToDate(debtsDomain[0].createTime)
        for (debtDomain in debtsDomain) {
            val newDate = stringToDate(debtDomain.createTime)
            if (isNewDate(newDate, lastDate)) {
                debts.add(ChatData.ChatDate(lastDate))
            }
            lastDate = newDate

            val isMessageOfUser = idCompare(userId, debtDomain.idUser)
            debts.add(
                ChatData.ChatMessage(
                    id = debtDomain.id,
                    isMessageOfUser = isMessageOfUser,
                    debtValue = calculateDebtValue(
                        isMessageOfUser,
                        debtDomain.debtType,
                        debtDomain.sum
                    ),
                    tag = debtDomain.tag,
                    isRead = true,
                    remoteTime = newDate
                )
            )
            if (debtDomain.id == debtsDomain.last().id) {
                debts.add(ChatData.ChatDate(lastDate))
            }
        }
        return debts
    }

    private fun isNewDate(newDate: Date, oldDate: Date): Boolean {
        val millisInDay = (60 * 60 * 24 * 1000).toLong()
        val newDateWithoutTime = removeTime(newDate)
        val oldDateWithoutTime = removeTime(oldDate)
        return (oldDateWithoutTime.time - newDateWithoutTime.time >= millisInDay)

    }

    private fun removeTime(date: Date): Date {
        val millisInDay = (60 * 60 * 24 * 1000).toLong()
        val currentTime = date.time
        val dateInMillisWithoutTime = (currentTime / millisInDay) * millisInDay
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

    private fun idCompare(userId: Int, debtId: Int): Boolean {
        return userId == debtId
    }
}