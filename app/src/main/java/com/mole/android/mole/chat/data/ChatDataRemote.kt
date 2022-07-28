package com.mole.android.mole.chat.data

import com.google.gson.annotations.SerializedName

data class ChatDataRemote(
    @SerializedName("debts")
    val debts: List<ChatDataDebtRemote>?,
    @SerializedName("debtor")
    val debtor: ChatDataDebtorRemote,
    @SerializedName("debtLeft")
    val debtLeft: Int
)

fun ChatDataRemote.asDomain(): ChatData {
    return ChatData(
        debts = ChatDataConverter.debtsAsDomain(debts, debtor.debtorInfo.idUser),
        debtor = debtor.asDomain(),
        debtLeft = debtLeft
    )
}
