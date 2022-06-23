package com.mole.android.mole.chat.data

data class ChatDataDebtorDomain(
    val debtorInfo: ChatDebtorInfo,
    val debtorStatistic: ChatDebtorStatistic,
    val mainPhotoUrl: ChatDebtorPhotoUrl
)

data class ChatDebtorInfo(
    val idUser: Int,
    val name: String,
    val mainPhotoName: String
)

data class ChatDebtorStatistic(
    val debtSum: Int,
    val debtCount: Int
)

data class ChatDebtorPhotoUrl(
    val photoSmall: String,
    val photoNormal: String
)
