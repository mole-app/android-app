package com.mole.android.mole.chat.data

import com.google.gson.annotations.SerializedName
import com.mole.android.mole.stringToDate

data class ChatDataDebtRemote(
    @SerializedName("id")
    val id: Int,
    @SerializedName("sum")
    val sum: Int,
    @SerializedName("idUser")
    val idUser: Int,
    @SerializedName("idCreator")
    val idCreator: Int,
    @SerializedName("isDelete")
    val isDelete: Boolean,
    @SerializedName("debtType")
    val debtType: DebtType,
    @SerializedName("createTime")
    val createTime: String,
    @SerializedName("tag")
    val tag: String?
)

fun ChatDataDebtRemote.asDomain(userId: Int): ChatDataDebtDomain {
    val isMessageOfUser = userId == idUser
    return ChatDataDebtDomain(
        id = id,
        isMessageOfCreator = isMessageOfUser,
        debtValue = calculateDebtValue(isMessageOfUser, debtType, sum),
        tag = tag,
        isRead = false,
        date = stringToDate(createTime)
    )
}

private fun calculateDebtValue(messageOfCreator: Boolean, debtType: DebtType, debtSum: Int): Int {
    return when (debtType) {
        DebtType.GIVE -> {
            if (messageOfCreator) debtSum
            else -1 * debtSum
        }
        DebtType.GET -> {
            if (messageOfCreator) -1 * debtSum
            else debtSum
        }
    }
}
