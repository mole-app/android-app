package com.mole.android.mole.chat.data

data class ChatData(
    val debts: List<ChatDebtsData>,
    val debtor: ChatDebtorData,
    val debtLeft: Int
)