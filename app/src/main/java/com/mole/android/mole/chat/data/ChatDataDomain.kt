package com.mole.android.mole.chat.data

data class ChatDataDomain(
    val debts: List<ChatDataDebtDomain>,
    val debtor: ChatDataDebtor,
    val debtLeft: Int
)
