package com.mole.android.mole.chat.data

import com.google.gson.annotations.SerializedName

data class ChatDataDomain(
    @SerializedName("debts")
    val debts: List<ChatDataDebtDomain>,
    @SerializedName("debtor")
    val debtor: ChatDataDebtorDomain,
    @SerializedName("debtLeft")
    val debtLeft: Int
)
