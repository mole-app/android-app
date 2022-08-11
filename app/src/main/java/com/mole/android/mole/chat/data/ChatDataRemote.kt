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

fun ChatDataRemote.asDomain(): ChatDataDomain {
    return ChatDataDomain(
        debts = debts?.let { debts ->
            debts.map { debt ->
                debt.asDomain(debtor.debtorInfo.idUser)
            }
        } ?: listOf(),
        debtor = debtor.asDomain(),
        debtLeft = debtLeft
    )
}
