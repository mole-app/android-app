package com.mole.android.mole.chat.data

import com.google.gson.annotations.SerializedName

data class ChatDataDebtorDomain(
    @SerializedName("debtorInfo")
    val debtorInfo: ChatDebtorInfo,
    @SerializedName("debtorStatistic")
    val debtorStatistic: ChatDebtorStatistic,
    @SerializedName("mainPhotoUrl")
    val mainPhotoUrl: ChatDebtorPhotoUrl
)

data class ChatDebtorInfo(
    @SerializedName("idUser")
    val idUser: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("mainPhotoName")
    val mainPhotoName: String
)

data class ChatDebtorStatistic(
    @SerializedName("debtSum")
    val debtSum: Int,
    @SerializedName("debtCount")
    val debtCount: Int
)

data class ChatDebtorPhotoUrl(
    @SerializedName("photoSmall")
    val photoSmall: String,
    @SerializedName("photoNormal")
    val photoNormal: String
)
